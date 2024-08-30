package com.xircle.apiserver.security

import com.xircle.core.store.member.MemberStore
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MemberDetailService(
    private val memberStore: MemberStore
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val member = memberStore.findMemberByEmail(email)
        return MemberDetails(member)
    }
}