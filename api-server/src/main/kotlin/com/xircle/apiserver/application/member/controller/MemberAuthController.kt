package com.xircle.apiserver.application.member.controller

import com.xircle.apiserver.application.member.dto.LoginRequest
import com.xircle.apiserver.application.member.dto.LoginResponse
import com.xircle.apiserver.application.member.dto.SignUpRequest
import com.xircle.apiserver.extension.toMemberInfo
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.domain.member.service.MemberAuthService
import com.xircle.core.util.TokenProvider
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class MemberAuthController(
    private val memberAuthService: MemberAuthService,
    private val tokenProvider: TokenProvider
) {
    @PostMapping(
        "/member",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun signUp(@Valid request: SignUpRequest): ResponseEntity<BaseResponse<Unit>> {
        val memberInfo = request.toMemberInfo()
        memberAuthService.signUp(memberInfo)
        return ResponseEntity.ok().body(BaseResponse(BaseResponseStatus.SUCCESS))
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<BaseResponse<LoginResponse>> {
        val member = memberAuthService.login(request.email, request.password)
        val token = tokenProvider.createAccessToken(member.id!!)
        val response = LoginResponse(token)
        return ResponseEntity.ok().body(BaseResponse(response))
    }
}