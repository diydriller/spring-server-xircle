package com.xircle.userservice.mapper

import com.xircle.userservice.application.dto.MemberDto
import com.xircle.userservice.presentation.dto.SignUpRequest
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface SignupMapper {
    companion object {
        val INSTANCE: SignupMapper = Mappers.getMapper(SignupMapper::class.java)
    }

    fun covertToDto(signUpRequest: SignUpRequest): MemberDto
}