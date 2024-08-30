package com.xircle.core.repository.member

import com.xircle.core.domain.member.model.FollowerFollowee
import com.xircle.core.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface FollowerFolloweeJpaRepository : JpaRepository<FollowerFollowee, Long> {
    fun findByFollowerAndFollowee(me: Member, other: Member): FollowerFollowee?
}