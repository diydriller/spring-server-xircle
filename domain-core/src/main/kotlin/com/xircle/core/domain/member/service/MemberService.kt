package com.xircle.core.domain.member.service

import com.xircle.core.domain.member.dto.MemberSearchCondition
import com.xircle.core.domain.member.model.Member
import com.xircle.core.store.member.MemberStore
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MemberService(private val memberStore: MemberStore) {
    @Transactional
    fun searchMember(page: Int, size: Int, userId: Long, memberSearchCondition: MemberSearchCondition): List<Member> {
        return memberStore.findMemberByCondition(page, size, userId, memberSearchCondition)
    }

    @Transactional
    fun getMemberProfile(memberId: Long): Member {
        return memberStore.findMemberProfileById(memberId)
    }
}