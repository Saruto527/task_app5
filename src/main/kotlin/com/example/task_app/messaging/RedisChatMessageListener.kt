package com.example.task_app.messaging

import com.example.task_app.domain.CommonMessage
import com.example.task_app.domain.ImageMessage
import com.example.task_app.domain.TextMessage
import com.example.task_app.service.api.ChatService
import com.example.task_app.service.utils.ObjectStringConverter
import org.slf4j.Logger
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono




@Component
class RedisChatMessageListener(
    private val logger: Logger,
    private val reactiveStringRedisTemplate: ReactiveStringRedisTemplate,
    private val objectStringConverter: ObjectStringConverter,
    private val chatService: ChatService
) {

    fun subscribeOnCommonMessageTopic(): Mono<Void>{
        return reactiveStringRedisTemplate.listenTo(PatternTopic(CommonMessage::class.java.name))
            .map { message -> message.message }
            .doOnNext { logger.info("Receive new message: $it") }
            .flatMap { objectStringConverter.stringToObject(it, CommonMessage :: class.java) }
            .flatMap { message ->
                when(message){
                    is TextMessage -> chatService.sendMessage(message)
                    is ImageMessage -> chatService.sendMessage(message)
                    else -> Mono.error(RuntimeException())

                }
            }
            .then()
    }



}