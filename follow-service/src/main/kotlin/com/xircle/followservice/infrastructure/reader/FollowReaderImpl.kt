package com.xircle.followservice.infrastructure.reader

import com.xircle.followservice.domain.integration.reader.FollowReader
import com.xircle.followservice.domain.model.MemberNode
import com.xircle.followservice.infrastructure.repository.MemberNodeRepository
import org.springframework.stereotype.Component

@Component
class FollowReaderImpl(
    private val memberNodeRepository: MemberNodeRepository
) : FollowReader {
    override fun findFollowers(followeeId: Long, page: Int, size: Int): List<MemberNode> {
        val skip = page * size
        return memberNodeRepository.findFollowers(followeeId, skip, size)
    }

    override fun findFollowees(followerId: Long, page: Int, size: Int): List<MemberNode> {
        val skip = page * size
        return memberNodeRepository.findFollowees(followerId, skip, size)
    }

    override fun existsFollowRelation(followerId: Long, followeeId: Long): Boolean {
        return memberNodeRepository.existsFollowRelation(followerId, followeeId)
    }

    override fun existsMember(memberId: Long): Boolean {
        return memberNodeRepository.existsById(memberId)
    }
}