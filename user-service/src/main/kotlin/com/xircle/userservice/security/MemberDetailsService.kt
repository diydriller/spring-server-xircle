package com.xircle.userservice.security

import com.xircle.userservice.domain.integration.reader.MemberReader
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(
    private val memberReader: MemberReader
) : UserDetailsService {
    override fun loadUserByUsername(username: String): MemberDetails {
        val member = memberReader.findMemberById(username.toLong())
        return MemberDetails(member)
    }
}