package com.xircle.userservice.presentation

import com.xircle.common.response.BaseResponse
import com.xircle.userservice.application.MemberProfileService
import com.xircle.userservice.domain.query.MemberSearchCondition
import com.xircle.userservice.presentation.dto.MemberProfile
import com.xircle.userservice.presentation.dto.MemberSearchResponse
import com.xircle.userservice.security.MemberDetails
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberProfileController(private val memberProfileService: MemberProfileService) {
    @GetMapping("/profile/member")
    fun searchMember(
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "10") size: Int,
        @Min(value = 1, message = "age must greater than 0")
        @RequestParam(value = "age", required = false) age: Int?,
        @Pattern(regexp = "^(서울대학교|고려대학교|연세대학교)$", message = "university must be either 서울대학교 or 고려대학교 or 연세대학교")
        @RequestParam(value = "university", required = false) university: String?,
        @Pattern(regexp = "^(남자|여자)$", message = "gender must be either 남자 or 여자")
        @RequestParam(value = "gender", required = false) gender: String?,
        @AuthenticationPrincipal memberDetails: MemberDetails
    ): ResponseEntity<BaseResponse<List<MemberSearchResponse>>> {
        val searchCondition = MemberSearchCondition(age, university, gender)
        val searchMemberResponseList =
            memberProfileService.searchMember(page, size, memberDetails.username.toLong(), searchCondition)
                .map {
                    MemberSearchResponse(it.id as Long, it.nickname)
                }
        return ResponseEntity.ok().body(BaseResponse(searchMemberResponseList))
    }

    @GetMapping("/profile/member/{memberId}")
    fun getMemberProfile(
        @Min(value = 1, message = "id must greater than 0")
        @PathVariable memberId: Long,
    ): ResponseEntity<BaseResponse<MemberProfile>> {
        val member = memberProfileService.getMemberProfile(memberId)
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