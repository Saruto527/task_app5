package com.example.task_app.data.model

import com.example.task_app.data.Priority

import java.time.LocalDateTime

data class TaskDto(



    val id: Long,
val description: String,
val isRemindersSet: Boolean,
val isTaskOpen: Boolean,
val createdOn: LocalDateTime,


)
