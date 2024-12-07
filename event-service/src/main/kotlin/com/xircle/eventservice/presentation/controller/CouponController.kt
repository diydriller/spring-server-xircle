package com.xircle.eventservice.presentation.controller

import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.eventservice.application.service.CouponService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CouponController(
    private val couponService: CouponService
) {
    @PostMapping("/reserve")
    fun reserveCoupon(
        @RequestHeader memberId: Long
    ): BaseResponse<Unit> {
        couponService.reserveCoupon(memberId)
        return BaseResponse(BaseResponseStatus.SUCCESS)
    }

    @PostMapping("/coupon")
    fun createCoupon(
        @RequestParam count: Int
    ): BaseResponse<Unit> {
        couponService.createCoupon(count)
        return BaseResponse(BaseResponseStatus.SUCCESS)
    }
}