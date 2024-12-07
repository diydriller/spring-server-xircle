package com.xircle.eventservice.infrastructure.repository

import com.xircle.eventservice.domain.model.Coupon
import com.xircle.eventservice.domain.model.CouponStatus
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CouponRepository : JpaRepository<Coupon, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT c FROM Coupon c WHERE c.status = :status ORDER BY c.id LIMIT 1")
    fun findAvailableCouponByStatusUsingPessimisticLock(status: CouponStatus): Coupon?
}