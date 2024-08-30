package com.xircle.core.store.member

import com.xircle.common.exception.ConflictException
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.domain.member.model.FollowerFollowee
import com.xircle.core.domain.member.model.Member
import com.xircle.core.repository.member.FollowerFolloweeJpaRepository
import com.xircle.core.repository.member.MemberJpaRepository
import org.springframework.stereotype.Component

@Component
class MemberStore(
    private val memberJpaRepository: MemberJpaRepository,
    private val followerFolloweeJpaRepository: FollowerFolloweeJpaRepository
) {
    fun findMemberByEmail(email: String): Member {
        return memberJpaRepository.findMemberByEmail(email)
            ?: throw ConflictException(BaseResponseStatus.NOT_EXIST_EMAIL)
    }

    fun saveMember(member: Member): Member {
        return memberJpaRepository.save(member)
    }

    fun findMemberById(id: Long): Member {
        return memberJpaRepository.findMemberById(id) ?: throw ConflictException(BaseResponseStatus.NOT_EXIST_EMAIL)
    }

    fun findFollow(me: Member, other: Member): FollowerFollowee? {
        return followerFolloweeJpaRepository.findByFollowerAndFollowee(me, other)
    }

    fun saveFollowerFollowee(followerFollowee: FollowerFollowee) {
        followerFolloweeJpaRepository.save(followerFollowee)
    }
}