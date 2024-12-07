package com.xircle.userservice.infrastructure.repository

import com.xircle.userservice.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface MemberRepository : JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    fun findMemberByEmail(email: String): Member?

    fun findMemberById(id: Long): Member?

    @Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.interestList WHERE m.id = :id")
    fun findMemberProfileById(id: Long): Member?
}