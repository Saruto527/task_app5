package com.example.task_app.cors.config

import com.example.task_app.handler.ChatWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping
import java.util.*

@Configuration
class ReactiveWebSocketConfig {

    @Bean
    fun webSocketHandlerMapping(chatWebSocketHandler: ChatWebSocketHandler): HandlerMapping{
        val map: MutableMap<String, WebSocketHandler> = HashMap()
        map["/ws/chat"] = chatWebSocketHandler

        val handlerMapping = SimpleUrlHandlerMapping()
        handlerMapping.setCorsConfigurations(Collections.singletonMap("*" ,CorsConfiguration().applyPermitDefaultValues()))
        handlerMapping.order = 1
        handlerMapping.urlMap = map
        return handlerMapping
    }


    @Bean
    fun handlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }


}