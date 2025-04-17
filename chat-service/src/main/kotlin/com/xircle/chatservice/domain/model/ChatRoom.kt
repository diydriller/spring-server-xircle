package com.xircle.chatservice.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "chat_room")
data class ChatRoom(
    @Id
    var id: String? = null,
    var name: String,
    @Field("user_id")
    var userId: Long,
    @Field("last_message_id")
    var lastMessageId: String? = null,
    @Field("last_read_message_id")
    var lastReadMessageId: String? = null,
    @Field("last_read_message_time")
    var lastReadMessageTime: LocalDateTime? = null
) : BaseEntity()
