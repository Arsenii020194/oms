package com.example.investments.oms.config

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import java.util.concurrent.CountDownLatch


@Configuration
@Profile("test")
class TestSecurityConfig {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .antMatchers("/**")
        }
    }

    @Bean
    fun kafkaConsumer(): KafkaConsumer {
        return KafkaConsumer()
    }
}

class KafkaConsumer {
    var latch = CountDownLatch(1)
        private set
    var payload: String? = null
        private set

    @KafkaListener(topics = ["orders"])
    fun receive(consumerRecord: ConsumerRecord<*, *>) {
        LOGGER.info("received payload='{}'", consumerRecord.toString())
        payload = consumerRecord.toString()
        latch.countDown()
    }

    fun resetLatch() {
        latch = CountDownLatch(1)
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }
}