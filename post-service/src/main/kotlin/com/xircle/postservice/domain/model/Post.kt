package com.xircle.postservice.domain.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize

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

    @JsonManagedReference
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "post", cascade = [CascadeType.PERSIST])
    val hashtagList: MutableList<Hashtag> = ArrayList()

    fun addHashtag(hashtag: Hashtag) {
        hashtagList.add(hashtag)
        hashtag.post = this
    }
}