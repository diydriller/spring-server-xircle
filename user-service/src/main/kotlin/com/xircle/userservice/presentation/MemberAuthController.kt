package com.xircle.userservice.presentation

import com.xircle.common.dto.MemberInfo
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.userservice.application.MemberAuthService
import com.xircle.userservice.auth.TokenService
import com.xircle.userservice.domain.integration.reader.MemberReader
import com.xircle.userservice.mapper.SignupMapper
import com.xircle.userservice.presentation.dto.LoginRequest
import com.xircle.userservice.presentation.dto.LoginResponse
import com.xircle.userservice.presentation.dto.SignUpRequest
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/user-service")
@RestController
class MemberAuthController(
    private val memberAuthService: MemberAuthService,
    private val tokenService: TokenService,
    private val memberReader: MemberReader
) {
    @PostMapping("/member", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun signUp(
        @Valid request: SignUpRequest
    ): ResponseEntity<BaseResponse<Unit>> {
        val signupDto = SignupMapper.INSTANCE.covertToDto(request)
        memberAuthService.signUp(signupDto)
        return ResponseEntity.ok().body(BaseResponse(BaseResponseStatus.SUCCESS))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: LoginRequest
    ): ResponseEntity<BaseResponse<LoginResponse>> {
        val member = memberAuthService.login(request.email, request.password)
        val token = tokenService.createAccessToken(member.id!!)
        val response = LoginResponse(token)
        return ResponseEntity.ok().body(BaseResponse(response))
    }

    @GetMapping("/member/{memberId}")
    fun getMemberInfo(
        @PathVariable memberId: Long
    ): MemberInfo {
        val member = memberReader.findMemberById(memberId)
        return MemberInfo(
            id = member.id!!,
            email = member.email
        )
    }
}