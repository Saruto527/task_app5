package com.example.task_app.handler

import com.example.task_app.event.WebSocketEvent
import java.util.*

data class SendTo(
    val userId: UUID,
    val event: WebSocketEvent
)
