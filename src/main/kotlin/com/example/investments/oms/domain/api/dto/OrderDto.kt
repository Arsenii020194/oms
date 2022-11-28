package com.example.investments.oms.domain.api.dto

import java.math.BigDecimal

data class OrderDto(
    val orderId: Long?,
    var orderStatus: Int?,
    val count: Int,
    val instrumentId: String,
    val price: BigDecimal,
    val type: Int,
    val userAccountId: Long,
    val userId: Long
)