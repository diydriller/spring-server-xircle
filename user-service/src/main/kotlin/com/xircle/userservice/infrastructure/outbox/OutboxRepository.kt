package com.xircle.userservice.infrastructure.outbox

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OutboxRepository : JpaRepository<Outbox, Long> {
    fun findAllByStatus(status: OutboxStatus): List<Outbox>
}