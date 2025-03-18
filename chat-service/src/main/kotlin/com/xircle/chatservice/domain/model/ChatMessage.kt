package com.xircle.chatservice.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "chat_message")
data class ChatMessage(
    @Id
    var id: String? = null,
    val message: String,
    @Field("room_id")
    val roomId: String,
    @Field("sender_id")
    val senderId: Long
) : BaseEntity()