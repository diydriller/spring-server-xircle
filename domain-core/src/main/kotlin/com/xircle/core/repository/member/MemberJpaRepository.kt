package com.xircle.core.repository.member

import com.xircle.core.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberJpaRepository : JpaRepository<Member, Long> {
    fun findMemberByEmail(email: String): Member?

    fun findMemberById(id: Long): Member?
}
