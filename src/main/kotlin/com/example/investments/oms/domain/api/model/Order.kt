package com.example.investments.oms.domain.api.model

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Digits

@Entity
@Table(name = "ORDERS")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val orderId: Long?,
    @Enumerated(EnumType.ORDINAL)
    var orderStatus: OrderStatus?,
    val count: Int,
    val instrumentId: String,
    @Column(scale = 2, precision = 8)
    @Digits(integer=10, fraction=2)
    val price: BigDecimal,
    val type: Int,
    val userAccountId: Long,
    val userId: Long
)