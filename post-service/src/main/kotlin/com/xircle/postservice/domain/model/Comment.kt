package com.xircle.postservice.domain.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
class Comment(
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post,
    var content: String,
    val depth: Int = 0,
    val memberId: Long
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    var parent: Comment? = null

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    val childrenList: MutableList<Comment> = mutableListOf()

    fun createRecomment(comment: Comment): Comment {
        comment.parent = this;
        this.childrenList.add(comment)
        return comment
    }
}