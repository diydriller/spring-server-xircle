package com.xircle.userservice.infrastructure.outbox

import com.xircle.userservice.domain.model.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
class Outbox(
    val topic: String,
    val payload: String,
    val className: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var status: OutboxStatus = OutboxStatus.PENDING
}