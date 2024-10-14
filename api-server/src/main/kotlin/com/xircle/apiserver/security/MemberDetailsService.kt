package com.xircle.apiserver.security

import com.xircle.core.repository.member.MemberJpaRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(
    private val memberRepository: MemberJpaRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): MemberDetails? {
        val member = memberRepository.findMemberById(username.toLong())
        return member?.let { MemberDetails(it) }
    }
}