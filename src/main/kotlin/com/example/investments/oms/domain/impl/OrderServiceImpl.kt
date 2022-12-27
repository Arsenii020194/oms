package com.example.investments.oms.domain.impl

import com.example.investments.oms.domain.api.OrderService
import com.example.investments.oms.domain.api.dto.OrderDto
import com.example.investments.oms.domain.api.model.Order
import com.example.investments.oms.domain.api.model.OrderStatus
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val objectMapper: ObjectMapper,
    private val kafkaTemplate: KafkaTemplate<String, Order>
) : OrderService {

    override fun createOrder(orderDto: OrderDto): Order {
        val order = objectMapper.convertValue(orderDto, Order::class.java)
        order.orderStatus = OrderStatus.CREATED
        kafkaTemplate.send(
            "orders",
            order.orderId.toString(),
            order
        )
        return orderRepository.save(order)
    }

    override fun updateOrder(orderDto: OrderDto): Order {
        return orderRepository
            .findById(orderDto.orderId!!)
            .map {
                val order = orderRepository.save(objectMapper.updateValue(it, orderDto))
                kafkaTemplate.send(
                    "orders",
                    order.orderId.toString(),
                    order
                )
                order
            }.orElseThrow()
    }

    override fun getOrder(id: Long): Order {
        return orderRepository.findById(id).map { objectMapper.convertValue(it, Order::class.java) }
            .orElseThrow()
    }

    override fun getAll(): List<Order> {
        return orderRepository.findAll().map { objectMapper.convertValue(it, Order::class.java) }
    }
}