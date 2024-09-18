package com.xircle.apiserver.application.member.controller

import com.xircle.apiserver.application.member.dto.MemberSearchResponse
import com.xircle.apiserver.security.MemberDetails
import com.xircle.common.response.BaseResponse
import com.xircle.apiserver.application.member.dto.MemberProfile
import com.xircle.core.domain.member.dto.MemberSearchCondition
import com.xircle.core.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(private val memberService: MemberService) {
    @GetMapping("/profile/member")
    fun searchMember(
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "age", required = false) age: Int?,
        @RequestParam(value = "university", required = false) university: String?,
        @RequestParam(value = "gender", required = false) gender: String?,
        @AuthenticationPrincipal memberDetails: MemberDetails
    ): ResponseEntity<BaseResponse<List<MemberSearchResponse>>> {
        val searchCondition = MemberSearchCondition(age, university, gender)
        val searchMemberResponseList =
            memberService.searchMember(page, size, memberDetails.getId() as Long, searchCondition)
                .map {
                    MemberSearchResponse(it.id as Long, it.nickname)
                }
        return ResponseEntity.ok().body(BaseResponse(searchMemberResponseList))
    }

    @GetMapping("/profile/member/{memberId}")
    fun getMemberProfile(
        @PathVariable memberId: Long,
    ): ResponseEntity<BaseResponse<MemberProfile>> {
        val member = memberService.getMemberProfile(memberId)
        val memberProfile = MemberProfile(
            email = member.email,
            gender = member.gender,
            age = member.age,
            job = member.job,
            nickname = member.nickname,
            modifier = member.modifier,
            introduction = member.introduction,
            address = member.address,
            university = member.university,
            isProfilePublic = member.isProfilePublic,
            isGraduate = member.isGraduate,
            phoneNumber = member.phoneNumber,
            workPlace = member.workPlace,
            resume = member.resume,
            isLocationPublic = member.isLocationPublic,
            latitude = member.latitude,
            longitude = member.longitude,
            interestList = member.interestList.map { it.title },
            profileImg = member.profileImage
        )
        return ResponseEntity.ok().body(BaseResponse(memberProfile))
    }
}