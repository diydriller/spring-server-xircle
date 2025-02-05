package com.xircle.outboxservice.infrastructure.repository

import com.xircle.outboxservice.infrastructure.model.Outbox
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OutboxRepository : JpaRepository<Outbox, Long> {
    fun findAllByStatus(status: Outbox.OutboxStatus): List<Outbox>
}