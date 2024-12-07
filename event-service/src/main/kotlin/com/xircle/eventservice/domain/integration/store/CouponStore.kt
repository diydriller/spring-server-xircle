package com.xircle.eventservice.domain.integration.store

import com.xircle.eventservice.domain.model.Coupon

interface CouponStore {
    fun createCoupon(couponList: List<Coupon>)
}