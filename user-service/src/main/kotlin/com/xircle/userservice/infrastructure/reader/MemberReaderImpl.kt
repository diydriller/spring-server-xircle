package com.xircle.userservice.infrastructure.reader

import com.xircle.common.exception.ConflictException
import com.xircle.common.exception.NotFoundException
import com.xircle.common.response.BaseResponseStatus
import com.xircle.userservice.domain.integration.reader.MemberReader
import com.xircle.userservice.domain.model.Member
import com.xircle.userservice.domain.query.MemberSearchCondition
import com.xircle.userservice.infrastructure.repository.member.MemberRepository
import com.xircle.userservice.infrastructure.repository.member.MemberSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class MemberReaderImpl(
    private val memberRepository: MemberRepository
) : MemberReader {
    override fun findMemberByEmail(email: String): Member {
        return memberRepository.findMemberByEmail(email)
            ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_EMAIL)
    }

    override fun isExistMemberByEmail(email: String) {
        memberRepository.findMemberByEmail(email)?.let {
            throw ConflictException(BaseResponseStatus.ALREADY_EXIST_EMAIL)
        }
    }

    override fun findMemberByIdListIn(memberIdList: List<Long>): List<Member> {
        return memberRepository.findMemberByIdListIn(memberIdList)
    }

    override fun findMemberById(id: Long): Member {
        return memberRepository.findMemberById(id) ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_EMAIL)
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
        return memberRepository.findAll(searchSpecification ?: Specification.where(null), pageable).toList()
    }

    override fun findMemberProfileById(memberId: Long): Member {
        return memberRepository.findMemberProfileById(memberId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_MEMBER)
    }
}