package com.example.investments.oms.domain.impl

import com.example.investments.oms.domain.api.model.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CrudRepository<Order, Long>