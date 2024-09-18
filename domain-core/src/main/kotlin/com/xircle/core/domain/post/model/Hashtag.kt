package com.xircle.core.domain.post.model

import com.xircle.core.domain.common.model.BaseEntity
import jakarta.persistence.*

@Entity
class Hashtag(
    val name: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = null
}