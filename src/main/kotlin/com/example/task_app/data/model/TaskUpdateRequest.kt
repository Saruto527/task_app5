package com.example.task_app.data.model

import com.example.task_app.data.Priority

data class TaskUpdateRequest(

    val description: String?,
    val isRemindersSet: Boolean?,
    val isTaskOpen: Boolean?,
    val priority: Priority?

)
