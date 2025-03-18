package com.xircle.userservice.domain.integration.reader

import com.xircle.userservice.domain.model.Member
import com.xircle.userservice.domain.query.MemberSearchCondition

interface MemberReader {
    fun findMemberById(id: Long): Member
    fun findMemberByCondition(
        page: Int,
        size: Int,
        userId: Long,
        memberSearchCondition: MemberSearchCondition
    ): List<Member>
    fun findMemberProfileById(memberId: Long): Member
    fun findMemberByEmail(email: String): Member
    fun isExistMemberByEmail(email: String)
    fun findMemberByIdListIn(memberIdList: List<Long>): List<Member>
}