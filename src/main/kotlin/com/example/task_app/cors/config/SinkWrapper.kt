package com.example.task_app.cors.config

import com.example.task_app.handler.SendTo
import org.springframework.stereotype.Component
import reactor.core.publisher.Sinks

@Component
class SinkWrapper {
    val sinks: Sinks.Many<SendTo> = Sinks.many().multicast().onBackpressureBuffer()
}