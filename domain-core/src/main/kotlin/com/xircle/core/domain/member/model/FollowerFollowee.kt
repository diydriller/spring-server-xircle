package com.xircle.core.domain.member.model

import com.xircle.core.domain.common.model.BaseEntity
import jakarta.persistence.*

@Entity
class FollowerFollowee(
    @ManyToOne
    @JoinColumn(name = "follower_id")
    val follower: Member,

    @ManyToOne
    @JoinColumn(name = "followee_id")
    val followee: Member
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follower_followee_id")
    var id: Long? = null
}