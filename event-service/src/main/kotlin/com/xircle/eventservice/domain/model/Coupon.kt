package com.xircle.eventservice.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Coupon : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    var status: CouponStatus = CouponStatus.AVAILABLE

    var memberId: Long? = null

    var reservedAt: LocalDateTime? = null

    fun reserve(memberId: Long) {
        this.memberId = memberId
        this.status = CouponStatus.RESERVED
        this.reservedAt = LocalDateTime.now()
    }
}