package com.example.investments.oms.domain.api

import com.example.investments.oms.domain.api.dto.OrderDto
import com.example.investments.oms.domain.api.model.Order

interface OrderService {
    fun createOrder(orderDto: OrderDto): Order
    fun updateOrder(orderDto: OrderDto): Order
    fun getOrder(id: Long): Order
    fun getAll(): List<Order>
}