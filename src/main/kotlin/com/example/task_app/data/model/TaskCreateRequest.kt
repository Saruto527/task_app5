package com.example.task_app.data.model

import com.example.task_app.data.Priority
import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class TaskCreateRequest(

@NotBlank(message = "Task description can't be empty")
    val description: String,

    val isRemindersSet: Boolean,

    val isTaskOpen: Boolean,

    val file: MultipartFile? = null


)
