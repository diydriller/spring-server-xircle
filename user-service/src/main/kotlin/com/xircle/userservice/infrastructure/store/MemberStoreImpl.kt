package com.xircle.userservice.infrastructure.store

import com.xircle.userservice.domain.integration.store.MemberStore
import com.xircle.userservice.domain.model.Member
import com.xircle.userservice.infrastructure.repository.member.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberStoreImpl(
    private val memberRepository: MemberRepository,
) : MemberStore {
    override fun saveMember(member: Member): Member {
        return memberRepository.save(member)
    }
}