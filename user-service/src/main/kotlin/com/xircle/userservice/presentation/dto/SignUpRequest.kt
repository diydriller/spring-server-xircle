package com.xircle.userservice.presentation.dto

import jakarta.validation.constraints.*
import org.springframework.web.multipart.MultipartFile

data class SignUpRequest(
    @field:NotEmpty(message = "field email is empty")
    @field:Pattern(regexp = "\\w+@(korea\\.ac\\.kr|snu\\.ac\\.kr|yonsei\\.ac\\.kr)", message = "email format is wrong")
    val email: String,

    @field:NotEmpty(message = "field password is empty")
    val password: String,

    @field:Pattern(regexp = "^(남자|여자)$", message = "gender must be either 남자 or 여자")
    @field:NotEmpty(message = "field gender is empty")
    val gender: String,

    @field:Min(value = 1, message = "age must greater than 0")
    @field:NotNull(message = "field age is null")
    val age: Int,

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

    @field:Pattern(regexp = "^(서울대학교|고려대학교|연세대학교)$", message = "university must be either 서울대학교 or 고려대학교 or 연세대학교")
    @field:NotEmpty(message = "field university is empty")
    val university: String,

    @field:NotNull(message = "field isPublic is null")
    val isProfilePublic: Boolean,

    @field:NotNull(message = "field isGraduate is null")
    val isGraduate: Boolean,

    @field:NotEmpty(message = "field phoneNumber is empty")
    @field:Pattern(regexp = "\\d{11}", message = "phoneNumber format is wrong")
    val phoneNumber: String,

    val workPlace: String?,

    val resume: String?,

    @field:NotNull(message = "field isLocationPublic is null")
    val isLocationPublic: Boolean = false,

    val latitude: Double?,

    val longitude: Double?,

    @field:Size(min = 1, message = "field interestArr is empty")
    val interestList: List<String>,

    @field:NotNull(message = "field profileImg is null")
    val profileImg: MultipartFile?
)
