package com.xircle.core.domain.member.model

import jakarta.persistence.*

@Entity
class Interest(
    title: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private val member: Member? = null

    fun createInterest(title: String): Interest {
        val interest = Interest(title = title)
        return interest
    }
}