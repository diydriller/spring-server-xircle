package com.xircle.eventservice.infrastructure.reader

import com.xircle.eventservice.domain.integration.reader.CouponReader
import com.xircle.eventservice.domain.model.Coupon
import com.xircle.eventservice.domain.model.CouponStatus
import com.xircle.eventservice.infrastructure.repository.CouponRepository
import org.springframework.stereotype.Component

@Component
class CouponReaderImpl(
    private val couponRepository: CouponRepository
) : CouponReader {
    override fun findAvailableCoupon(): Coupon? {
        return couponRepository.findAvailableCouponByStatusUsingPessimisticLock(CouponStatus.AVAILABLE)
    }
}