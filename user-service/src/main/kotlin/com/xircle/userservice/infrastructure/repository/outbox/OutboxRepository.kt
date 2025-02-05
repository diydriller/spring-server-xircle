package com.xircle.userservice.infrastructure.repository.outbox

import com.xircle.userservice.infrastructure.model.Outbox
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional("sharedTransactionManager")
@Repository
interface OutboxRepository : JpaRepository<Outbox, Long> {
    fun findAllByStatus(status: Outbox.OutboxStatus): List<Outbox>
}