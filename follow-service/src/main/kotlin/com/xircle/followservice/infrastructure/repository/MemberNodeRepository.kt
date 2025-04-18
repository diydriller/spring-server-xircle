package com.xircle.followservice.infrastructure.repository

import com.xircle.followservice.domain.model.MemberNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface MemberNodeRepository : Neo4jRepository<MemberNode, Long> {
    @Query(
        "OPTIONAL MATCH (u:Member)-[:FOLLOWS]->(f:Member) " +
                "WHERE u.id = \$followeeId " +
                "RETURN DISTINCT f"
    )
    fun findFollowerList(followeeId: Long): List<MemberNode>

    @Query(
        "OPTIONAL MATCH (u:Member)<-[:FOLLOWS]-(f:Member) " +
                "WHERE u.id = \$followerId " +
                "RETURN DISTINCT f"
    )
    fun findFolloweeList(followerId: Long): List<MemberNode>

    @Query(
        "OPTIONAL MATCH (follower:Member {id: \$followerId})-[r:FOLLOWS]->(followee:Member {id: \$followeeId}) " +
                "RETURN COUNT(r) > 0"
    )
    fun existsFollowRelation(followerId: Long, followeeId: Long): Boolean

    @Query(
        "OPTIONAL MATCH (follower:Member {id: \$followerId}), (followee:Member {id: \$followeeId}) " +
                "MERGE (follower)-[:FOLLOWS]->(followee)"
    )
    fun follow(followerId: Long, followeeId: Long)

    @Query(
        "OPTIONAL MATCH (follower:Member {id: \$followerId})-[r:FOLLOWS]->(followee:Member {id: \$followeeId}) " +
                "DELETE r"
    )
    fun unfollow(followerId: Long, followeeId: Long)

    @Query(
        "OPTIONAL MATCH (a:Member)-[:FOLLOWS]->(b:Member)-[:FOLLOWS]->(c:Member) " +
                "WHERE a.id = \$memberId AND NOT (a)-[:FOLLOWS]->(c) AND a.id <> c.id " +
                "RETURN DISTINCT c LIMIT 20"
    )
    fun recommendFollowerOfFollower(memberId: Long): List<MemberNode>
}