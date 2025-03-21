package com.xircle.chatservice.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "chat_room")
data class ChatRoom(
    @Id
    var id: String? = null,
    var name: String,
    @Field("last_message_id")
    var lastMessageId: String? = null
) : BaseEntity()
