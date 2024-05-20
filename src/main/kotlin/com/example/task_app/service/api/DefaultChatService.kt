package com.example.task_app.service.api

import com.example.task_app.cors.config.SinkWrapper
import com.example.task_app.domain.ChatMember
import com.example.task_app.domain.CommonMessage
import com.example.task_app.domain.TextMessage
import com.example.task_app.event.ChatMessageEvent
import com.example.task_app.event.NewMessageEvent
import com.example.task_app.event.WebSocketEvent
import com.example.task_app.handler.SendTo
import com.example.task_app.messaging.RedisChatMessagePublisher
import com.example.task_app.repository.ChatRepository
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import java.time.LocalDateTime
import java.util.*


@Service
class DefaultChatService(
    val logger: Logger,
    val sinkWrapper: SinkWrapper,
    val chatRepository: ChatRepository,
    val redisChatPublisher: RedisChatMessagePublisher
): ChatService {
    override fun handleNewMessageEvent(senderId: UUID, newMessageEvent: NewMessageEvent): Mono<Void> {
        logger.info("Receive NewMessageEvent from $senderId: $newMessageEvent")
        return chatRepository.findById(newMessageEvent.chatId)
            .filter { it.chatMembers.map(ChatMember :: userId).contains(senderId) }
            .flatMap { chat ->
                val textMessage = TextMessage(UUID.randomUUID(), chat.chatId, chat.chatMembers.first{it.userId == senderId}, newMessageEvent.content, LocalDateTime.now(), false)
                chat.lastMessage = textMessage
                return@flatMap Mono.zip(chatRepository.save(chat), Mono.just(textMessage))

            }
            .flatMap { broadcastMessage(it.t2) }
    }

    override fun markPreviousMessageAsRead(messageId: UUID): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun sendEventToUserId(userId: UUID, webSocketEvent: WebSocketEvent): Mono<Void> {
        return Mono.fromCallable { sinkWrapper.sinks.emitNext(SendTo(userId, webSocketEvent), Sinks.EmitFailureHandler.FAIL_FAST) }
            .then()
    }

    override fun sendMessage(message: CommonMessage): Mono<Void> {
        return chatRepository.findById(message.chatId)
            .map { it.chatMembers }
            .flatMapMany { Flux.fromIterable(it) }
            .flatMap { member -> sendEventToUserId(member.userId, ChatMessageEvent(message.chatId,message))    }
            .then()
    }

    override fun broadcastMessage(commonMessage: CommonMessage): Mono<Void> {
        return redisChatPublisher.broadcastMessage(commonMessage)
    }


}