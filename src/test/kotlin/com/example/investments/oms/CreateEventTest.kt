package com.example.investments.oms

import com.example.investments.oms.config.KafkaConsumer
import com.example.investments.oms.domain.api.dto.OrderDto
import com.example.investments.oms.domain.api.model.Order
import com.example.investments.oms.domain.api.model.OrderStatus
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"]
)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
class CreateEventTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var consumer: KafkaConsumer

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @Throws(Exception::class)
    fun givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() {
        val data = OrderDto(
            orderId = null,
            orderStatus = null,
            count = 78,
            instrumentId = "test",
            price = BigDecimal.valueOf(97.27),
            type = 28,
            userAccountId = 123,
            userId = 123
        )
        val actualString = mockMvc.post("/orders") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(data)
        }.andExpect {
            status { isCreated() }
        }.andReturn().response.contentAsString
        val actual = objectMapper.readValue(actualString, Order::class.java)
        val messageConsumed: Boolean = consumer.latch.await(10, TimeUnit.SECONDS)
        assertTrue(messageConsumed)

        assertEquals(OrderStatus.CREATED, actual.orderStatus)
        assertEquals(data.count, actual.count)
        assertEquals(data.instrumentId, actual.instrumentId)
        assertEquals(data.price, actual.price)
        assertEquals(data.type, actual.type)
        assertEquals(data.userAccountId, actual.userAccountId)
        assertEquals(data.userId, actual.userId)
    }

}