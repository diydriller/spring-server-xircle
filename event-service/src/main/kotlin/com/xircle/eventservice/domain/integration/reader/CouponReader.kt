package com.xircle.eventservice.domain.integration.reader

import com.xircle.eventservice.domain.model.Coupon

interface CouponReader {
    fun findAvailableCoupon(): Coupon?
}