package com.xircle.userservice.security

import com.xircle.userservice.domain.model.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MemberDetails(
    private val member: Member
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = ArrayList<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(member.role.name))
        return authorities
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.id.toString()
    }
}