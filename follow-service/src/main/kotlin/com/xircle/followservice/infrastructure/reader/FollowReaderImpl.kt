package com.xircle.followservice.infrastructure.reader

import com.xircle.followservice.domain.integration.reader.FollowReader
import com.xircle.followservice.domain.model.MemberNode
import com.xircle.followservice.infrastructure.repository.MemberNodeRepository
import org.springframework.stereotype.Component

@Component
class FollowReaderImpl(
    private val memberNodeRepository: MemberNodeRepository
) : FollowReader {
    override fun findFollowerList(followeeId: Long): List<MemberNode> {
        return memberNodeRepository.findFollowerList(followeeId)
    }

    override fun findFolloweeList(followerId: Long): List<MemberNode> {
        return memberNodeRepository.findFolloweeList(followerId)
    }

    override fun existsFollowRelation(followerId: Long, followeeId: Long): Boolean {
        return memberNodeRepository.existsFollowRelation(followerId, followeeId)
    }

    override fun existsMember(memberId: Long): Boolean {
        return memberNodeRepository.existsById(memberId)
    }
}