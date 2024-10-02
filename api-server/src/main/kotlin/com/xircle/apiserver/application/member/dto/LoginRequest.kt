package com.xircle.apiserver.application.member.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class LoginRequest(
    @field:NotEmpty(message = "field email is empty")
    @field:Pattern(regexp = "\\w+@(korea\\.ac\\.kr|snu\\.ac\\.kr|yonsei\\.ac\\.kr)", message = "email format is wrong")
    val email: String = "",

    @field:NotEmpty(message = "field password is empty")
    val password: String = "",
)