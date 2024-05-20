package com.example.task_app.service.api

import com.example.task_app.domain.CommonMessage
import com.example.task_app.event.NewMessageEvent
import com.example.task_app.event.WebSocketEvent
import reactor.core.publisher.Mono
import java.util.*

interface ChatService {
    fun handleNewMessageEvent(senderId: UUID, newMessageEvent: NewMessageEvent): Mono<Void>
    fun markPreviousMessageAsRead(messageId:UUID): Mono<Void>
    fun sendEventToUserId(userId: UUID, webSocketEvent: WebSocketEvent): Mono<Void>
    fun sendMessage(message: CommonMessage): Mono<Void>
    fun broadcastMessage(commonMessage: CommonMessage): Mono<Void>

}