package com.xircle.jpa.store.member

import com.xircle.common.exception.ConflictException
import com.xircle.common.exception.NotFoundException
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.domain.member.dto.MemberSearchCondition
import com.xircle.core.domain.member.model.FollowerFollowee
import com.xircle.core.domain.member.model.Member
import com.xircle.core.store.member.MemberStore
import com.xircle.jpa.repository.member.FollowerFolloweeJpaRepository
import com.xircle.jpa.repository.member.MemberJpaRepository
import com.xircle.jpa.repository.member.MemberSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class MemberStoreImpl(
    private val memberJpaRepository: MemberJpaRepository,
    private val followerFolloweeJpaRepository: FollowerFolloweeJpaRepository,
) : MemberStore {
    override fun findMemberByEmail(email: String): Member {
        return memberJpaRepository.findMemberByEmail(email)
            ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_EMAIL)
    }

    override fun isExistMemberByEmail(email: String) {
        memberJpaRepository.findMemberByEmail(email)?.let {
            throw ConflictException(BaseResponseStatus.ALREADY_EXIST_EMAIL)
        }
    }

    override fun saveMember(member: Member): Member {
        return memberJpaRepository.save(member)
    }

    override fun findMemberById(id: Long): Member {
        return memberJpaRepository.findMemberById(id) ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_EMAIL)
    }

    override fun findFollow(me: Member, other: Member): FollowerFollowee? {
        return followerFolloweeJpaRepository.findByFollowerAndFollowee(me, other)
    }

    override fun saveFollowerFollowee(followerFollowee: FollowerFollowee) {
        followerFolloweeJpaRepository.save(followerFollowee)
    }

    override fun findMemberByCondition(
        page: Int,
        size: Int,
        userId: Long,
        memberSearchCondition: MemberSearchCondition
    ): List<Member> {
        val pageable: Pageable = PageRequest.of(page, size)
        val searchSpecification = listOfNotNull(
            MemberSpecification.notEqualId(userId),
            MemberSpecification.equalAge(memberSearchCondition.age),
            MemberSpecification.equalUniversity(memberSearchCondition.university),
            MemberSpecification.equalGender(memberSearchCondition.gender)
        ).reduceOrNull(Specification<Member>::and)
        return memberJpaRepository.findAll(searchSpecification ?: Specification.where(null), pageable).toList()
    }

    override fun findMemberProfileById(memberId: Long): Member {
        return memberJpaRepository.findMemberProfileById(memberId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_MEMBER)
    }
}