package com.xircle.core.domain.post.model

import com.xircle.core.domain.common.model.BaseEntity
import jakarta.persistence.*

@Entity
class Post(
    val content: String,
    val title: String,
    val postImgSrc: String,
    val memberId: Long
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    var id: Long? = null

    @OneToMany(mappedBy = "post", cascade = [CascadeType.PERSIST])
    val hashtagList: MutableList<Hashtag> = ArrayList()

    fun addHashtag(hashtag: Hashtag) {
        hashtagList.add(hashtag)
        hashtag.post = this
    }
}