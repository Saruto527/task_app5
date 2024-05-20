package com.example.task_app.domain

import java.util.*


data class ChatMember(
    val userId: UUID,
    var fullName: String,
    var avatar: String,
    var deletedChat: Boolean
)
