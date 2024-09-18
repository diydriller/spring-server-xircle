package com.xircle.core.store.member

import com.xircle.common.exception.ConflictException
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.domain.member.dto.MemberSearchCondition
import com.xircle.core.domain.member.model.FollowerFollowee
import com.xircle.core.domain.member.model.Member
import com.xircle.core.repository.member.FollowerFolloweeJpaRepository
import com.xircle.core.repository.member.MemberJpaRepository
import com.xircle.core.repository.member.MemberSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
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

    fun findMemberByCondition(page: Int, size: Int, userId: Long, memberSearchCondition: MemberSearchCondition): List<Member> {
        val pageable: Pageable = PageRequest.of(page, size)
        val searchSpecification = listOfNotNull(
            MemberSpecification.notEqualId(userId),
            MemberSpecification.equalAge(memberSearchCondition.age),
            MemberSpecification.equalUniversity(memberSearchCondition.university),
            MemberSpecification.equalGender(memberSearchCondition.gender)
        ).reduceOrNull(Specification<Member>::and)
        return memberJpaRepository.findAll(searchSpecification ?: Specification.where(null), pageable).toList()
    }

    fun findMemberProfileById(memberId: Long): Member {
        return memberJpaRepository.findMemberProfileById(memberId) ?: throw ConflictException(BaseResponseStatus.NOT_EXIST_MEMBER)
    }
}