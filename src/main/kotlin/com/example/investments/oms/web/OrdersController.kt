package com.example.investments.oms.web

import com.example.investments.oms.domain.api.OrderService
import com.example.investments.oms.domain.api.dto.OrderDto
import com.example.investments.oms.domain.api.model.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrdersController(private val orderService: OrderService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@RequestBody orderDto: OrderDto): Order {
        return orderService.createOrder(orderDto)
    }

    @PutMapping
    fun updateOrder(@RequestBody orderDto: OrderDto): Order {
        return orderService.updateOrder(orderDto)
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Long): Order {
        return orderService.getOrder(id)
    }

    @GetMapping
    fun getAll(): List<Order> {
        return orderService.getAll()
    }
}