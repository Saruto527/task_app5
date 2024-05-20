package com.example.task_app.repository

import com.example.task_app.domain.Chat
import reactor.core.publisher.Mono
import java.util.*


interface ChatRepository {

    fun findById(id: UUID): Mono<Chat>
    fun save(chat: Chat): Mono<Chat>


}