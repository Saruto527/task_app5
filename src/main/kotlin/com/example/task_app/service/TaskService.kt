package com.example.task_app.service

import com.example.task_app.data.Task
import com.example.task_app.data.model.TaskCreateRequest
import com.example.task_app.data.model.TaskDto
import com.example.task_app.data.model.TaskUpdateRequest
import com.example.task_app.exception.TaskNotFoundException
import com.example.task_app.repository.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field
import java.util.stream.Collectors
import kotlin.reflect.full.memberProperties


@Service
class TaskService(private val repository: TaskRepository, private val fileService: UploadService) {

    private fun mappingEntityToDto(task: Task) = TaskDto(
           task.id,
           task.description,
           task.isRemindersSet,
           task.isTaskOpen,
           task.createdOn,
           task.file
    )

    private fun mappingFromRequestToEntity(task: Task, taskCreateRequest: TaskCreateRequest){
        task.description = taskCreateRequest.description
        task.isRemindersSet = taskCreateRequest.isRemindersSet
        task.isTaskOpen = taskCreateRequest.isTaskOpen
    }

    private fun checkTaskForId(id: Long){
        if(repository.existsById(id)){
            throw TaskNotFoundException("Task with id $id does not exist")
        }
    }


    fun getTaskById(id: Long): TaskDto {

        checkTaskForId(id)
        val task: Task = repository.findTaskById(id)
        return mappingEntityToDto(task)
    }

    fun getAllTasks(): List<TaskDto> =
        repository.findAll().stream().map(this:: mappingEntityToDto).collect(Collectors.toList())


    fun getAllOpenTasks(): List<TaskDto> =
        repository.queryAllOpenTask().stream().map(this:: mappingEntityToDto).collect(Collectors.toList())

    fun getAllClosedTasks(): List<TaskDto> =
        repository.queryAllClosedTask().stream().map(this:: mappingEntityToDto).collect(Collectors.toList())

    fun createTask(request: TaskCreateRequest): TaskDto{
//        if(repository.doesDescriptionExist(request.description)){
//            throw BadRequestException("There is already a task with the description ${request.description}")
//        }

        val task = Task()
        val file = fileService.upload(request.file)

        when(file){
            FileStatus.Empty -> throw Exception("File is empty, please check to upload a valid file!")
            FileStatus.Existing -> throw Exception("File already exists, please upload a new file!")
            FileStatus.Omitted -> Unit
            is FileStatus.Safe -> task.file = file.file
        }
        mappingFromRequestToEntity(task, request)
        val savedTask: Task = repository.save(task)
        return mappingEntityToDto(savedTask)

    }

    fun updateTask(id: Long, request: TaskUpdateRequest): TaskDto{
        checkTaskForId(id)
        val existingTask : Task = repository.findTaskById(id)


        for(prop in TaskUpdateRequest:: class.memberProperties){
            if(prop.get(request) != null){
                val field: Field? = ReflectionUtils.findField(Task::class.java, prop.name)

                field?.let {
                    it.isAccessible = true
                    ReflectionUtils.setField(it, existingTask, prop.get(request))
                }
            }
        }

        val savedTask: Task = repository.save(existingTask)
        return mappingEntityToDto(savedTask )

    }

    fun deleteTask(id: Long): String{
        checkTaskForId(id)
        repository.deleteById(id)
        return "Task with the ID: $id has been deleted."
    }




}


