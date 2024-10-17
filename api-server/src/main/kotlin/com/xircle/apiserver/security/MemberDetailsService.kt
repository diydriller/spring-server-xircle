package com.xircle.apiserver.security

import com.xircle.core.store.member.MemberStore
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(
    private val memberStore: MemberStore
) : UserDetailsService {
    override fun loadUserByUsername(username: String): MemberDetails {
        val member = memberStore.findMemberById(username.toLong())
        return MemberDetails(member)
    }
}