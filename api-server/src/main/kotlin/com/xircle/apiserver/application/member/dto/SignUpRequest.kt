package com.xircle.apiserver.application.member.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile

data class SignUpRequest(
    @field:NotEmpty(message = "field email is empty")
    @field:Pattern(regexp = "\\w+@(korea\\.ac\\.kr|snu\\.ac\\.kr|yonsei\\.ac\\.kr)", message = "email format is wrong")
    var email: String,

    @field:NotEmpty(message = "field password is empty")
    val password: String,

    @field:NotEmpty(message = "field gender is empty")
    val gender: String,

    @field:NotNull(message = "field age is empty")
    val age : Int,

    @field:NotEmpty(message = "field job is empty")
    val job: String,

    @field:NotEmpty(message = "field nickname is empty")
    val nickname: String,

    @field:NotEmpty(message = "field modifier is empty")
    val modifier: String,

    @field:NotEmpty(message = "field introduction is empty")
    val introduction: String,

    @field:NotEmpty(message = "field address is empty")
    val address: String,

    @field:NotEmpty(message = "field university is empty")
    val university: String,

    @field:NotNull(message = "field isPublic is empty")
    val isProfilePublic: Boolean,

    @field:NotNull(message = "field isGraduate is empty")
    val isGraduate: Boolean,

    @field:NotEmpty(message = "field phoneNumber is empty")
    @field:Pattern(regexp = "\\d{11}", message = "phoneNumber format is wrong")
    val phoneNumber: String,

    val workPlace: String?,

    val resume: String?,

    @field:NotNull(message = "field isLocationPublic is empty")
    val isLocationPublic: Boolean,

    val latitude: Double?,

    val longitude: Double?,

    @field:Size(min = 1, message = "field interestArr is empty")
    val interestList: List<String> = ArrayList(),

    @field:NotNull(message = "field profileImg is empty")
    val profileImg: MultipartFile
)
