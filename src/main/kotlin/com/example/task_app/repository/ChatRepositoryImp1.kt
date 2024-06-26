package com.example.task_app.repository

import com.example.task_app.domain.Chat
import com.example.task_app.domain.ChatMember
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class ChatRepositoryImp1 : ChatRepository {
    final val chats = ConcurrentHashMap<UUID, Chat>()

    init {
        val chat = Chat(UUID.fromString("72b196c8-2e3c-44a8-acb2-039afb0e8f04"), listOf(ChatMember(UUID.fromString("dde626da-66a7-41ab-8e13-7664d1b112a7"), "Bogatikov Anton", "http://avatars", false)), LocalDateTime.now(), null)
        chats[chat.chatId] = chat
    }

    override fun findById(id: UUID): Mono<Chat> {
        return Mono.justOrEmpty(chats[id])
    }

    override fun save(chat: Chat): Mono<Chat> {
        chats[chat.chatId] = chat
       return Mono.justOrEmpty(chat)
    }

}