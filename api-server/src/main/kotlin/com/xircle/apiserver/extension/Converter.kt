package com.xircle.apiserver.extension

import com.xircle.apiserver.application.member.dto.SignUpRequest
import com.xircle.apiserver.application.post.dto.CreatePostRequest
import com.xircle.core.domain.member.dto.MemberInfo
import com.xircle.core.domain.post.dto.PostInfo

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
        profileImg = this.profileImg!!,
    )
}

fun CreatePostRequest.toPostInfo(memberId: Long): PostInfo {
    return PostInfo(
        title = this.title,
        content = this.content,
        hashtagList = this.hashtagList,
        postImg = this.postImg,
        memberId = memberId
    )
}