package com.example.investments.oms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class OmsApplication

fun main(args: Array<String>) {
	runApplication<OmsApplication>(*args)
}
