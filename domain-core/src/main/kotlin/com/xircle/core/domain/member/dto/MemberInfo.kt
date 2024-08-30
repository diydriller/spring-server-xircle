package com.xircle.core.domain.member.dto

import org.springframework.web.multipart.MultipartFile

data class MemberInfo(
    var email: String,
    val password: String,
    val gender: String,
    val age : Int,
    val job: String,
    val nickname: String,
    val modifier: String,
    val introduction: String,
    val address: String,
    val university: String,
    val isProfilePublic: Boolean,
    val isGraduate: Boolean,
    val phoneNumber: String,
    val workPlace: String?,
    val resume: String?,
    val isLocationPublic: Boolean,
    val latitude: Double?,
    val longitude: Double?,
    val interestList: List<String> = ArrayList(),
    val profileImg: MultipartFile
)

