package com.xircle.userservice.infrastructure.model

import com.xircle.userservice.domain.model.BaseEntity
import jakarta.persistence.*


@Entity
class Outbox(
    val topic: String,
    val payload: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Enumerated(EnumType.STRING)
    var status: OutboxStatus = OutboxStatus.PENDING

    enum class OutboxStatus {
        PENDING, FAILED
    }
}