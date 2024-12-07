package com.xircle.userservice.presentation.dto

data class MemberProfile(
    var email: String,
    val gender: String,
    val age: Int,
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
    val profileImg: String?
)