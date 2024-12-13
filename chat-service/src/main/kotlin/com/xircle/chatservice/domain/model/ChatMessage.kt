package com.xircle.chatservice.domain.model

import jakarta.persistence.*

@Entity
class ChatMessage : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_massage_id")
    var id: Long? = null
}