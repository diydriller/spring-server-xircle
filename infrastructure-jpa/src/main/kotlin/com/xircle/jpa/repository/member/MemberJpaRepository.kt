package com.xircle.jpa.repository.member

import com.xircle.core.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface MemberJpaRepository : JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    fun findMemberByEmail(email: String): Member?

    fun findMemberById(id: Long): Member?

    @Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.interestList WHERE m.id = :id")
    fun findMemberProfileById(id: Long): Member?
}
