package com.example.investments.oms.domain.api.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long?,
    @Enumerated(EnumType.ORDINAL)
    var orderStatus: OrderStatus?,
    val count: Int,
    val instrumentId: String,
    val price: BigDecimal,
    val type: Int,
    val userAccountId: Long,
    val userId: Long
)