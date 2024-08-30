package com.xircle.apiserver.extension

import com.xircle.apiserver.application.member.dto.SignUpRequest
import com.xircle.core.domain.member.dto.MemberInfo

fun SignUpRequest.toMemberInfo(): MemberInfo {
    return MemberInfo(
        email = this.email,
        password = this.password,
        gender = this.gender,
        age = this.age,
        job = this.job,
        nickname = this.nickname,
        modifier = this.modifier,
        introduction = this.introduction,
        address = this.address,
        university = this.university,
        isProfilePublic = this.isProfilePublic,
        isGraduate = this.isGraduate,
        phoneNumber = this.phoneNumber,
        workPlace = this.workPlace,
        resume = this.resume,
        isLocationPublic = this.isLocationPublic,
        latitude = this.latitude,
        longitude = this.longitude,
        interestList = this.interestList,
        profileImg = this.profileImg,
    )
}