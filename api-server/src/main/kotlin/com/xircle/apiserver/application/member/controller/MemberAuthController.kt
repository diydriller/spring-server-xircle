package com.xircle.apiserver.application.member.controller

import com.xircle.apiserver.application.member.dto.SignUpRequest
import com.xircle.apiserver.extension.toMemberInfo
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.domain.member.service.MemberAuthService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberAuthController(private val memberAuthService: MemberAuthService) {
    @PostMapping(
        "/member",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun signUp(request: SignUpRequest): ResponseEntity<BaseResponse<Unit>> {
        val memberInfo = request.toMemberInfo()
        memberAuthService.signUp(memberInfo)
        return ResponseEntity.ok().body(BaseResponse(BaseResponseStatus.SUCCESS))
    }
}