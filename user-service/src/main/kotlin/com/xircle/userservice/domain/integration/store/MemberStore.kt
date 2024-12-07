package com.xircle.userservice.domain.integration.store

import com.xircle.userservice.domain.model.Member
import org.springframework.stereotype.Repository

@Repository
interface MemberStore {
    fun saveMember(member: Member): Member
}