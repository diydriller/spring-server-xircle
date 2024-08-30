package com.xircle.apiserver.application.member.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile

data class SignUpRequest(
    @NotEmpty(message = "field email is empty")
    @Pattern(regexp = "\\w+@\\w+.\\w+", message = "email format is wrong")
    var email: String,

    @NotEmpty(message = "field password is empty")
    val password: String,

    @NotEmpty(message = "field gender is empty")
    val gender: String,

    @NotNull(message = "field age is empty")
    val age : Int,

    @NotEmpty(message = "field job is empty")
    val job: String,

    @NotEmpty(message = "field nickname is empty")
    val nickname: String,

    @NotEmpty(message = "field modifier is empty")
    val modifier: String,

    @NotEmpty(message = "field introduction is empty")
    val introduction: String,

    @NotEmpty(message = "field address is empty")
    val address: String,

    @NotEmpty(message = "field university is empty")
    val university: String,

    @NotNull(message = "field isPublic is empty")
    val isProfilePublic: Boolean,

    @NotNull(message = "field isGraduate is empty")
    val isGraduate: Boolean,

    @NotEmpty(message = "field phoneNumber is empty")
    @Pattern(regexp = "\\d{11}", message = "phoneNumber format is wrong")
    val phoneNumber: String,

    val workPlace: String?,

    val resume: String?,

    @NotNull(message = "field isLocationPublic is empty")
    val isLocationPublic: Boolean,

    val latitude: Double?,

    val longitude: Double?,

    @Size(min = 1, message = "field interestArr is empty")
    val interestList: List<String> = ArrayList(),

    @NotNull(message = "field profileImg is empty")
    val profileImg: MultipartFile
)
