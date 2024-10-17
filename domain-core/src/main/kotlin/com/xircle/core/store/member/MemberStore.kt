package com.xircle.core.store.member

import com.xircle.core.domain.member.dto.MemberSearchCondition
import com.xircle.core.domain.member.model.FollowerFollowee
import com.xircle.core.domain.member.model.Member
import org.springframework.stereotype.Repository

@Repository
interface MemberStore {
    fun findMemberByEmail(email: String): Member
    fun isExistMemberByEmail(email: String)
    fun saveMember(member: Member): Member
    fun findMemberById(id: Long): Member
    fun findFollow(me: Member, other: Member): FollowerFollowee?
    fun saveFollowerFollowee(followerFollowee: FollowerFollowee)
    fun findMemberByCondition(
        page: Int,
        size: Int,
        userId: Long,
        memberSearchCondition: MemberSearchCondition
    ): List<Member>
    fun findMemberProfileById(memberId: Long): Member
}