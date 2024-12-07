package com.xircle.eventservice.application.service

import com.xircle.common.exception.NotFoundException
import com.xircle.common.response.BaseResponseStatus
import com.xircle.eventservice.domain.integration.reader.CouponReader
import com.xircle.eventservice.domain.integration.store.CouponStore
import com.xircle.eventservice.domain.model.Coupon
import org.antlr.v4.runtime.IntStream
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CouponService(
    private val couponReader: CouponReader,
    private val couponStore: CouponStore
) {
    @Transactional
    fun reserveCoupon(memberId: Long) {
        val coupon = couponReader.findAvailableCoupon()
            ?: throw NotFoundException(BaseResponseStatus.NOT_AVAILABLE_COUPON)
        coupon.reserve(memberId)
    }

    @Transactional
    fun createCoupon(count: Int) {
        val couponList = List(count) {
            Coupon()
        }
        couponStore.createCoupon(couponList)
    }
}