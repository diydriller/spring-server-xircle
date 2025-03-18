package com.xircle.chatservice.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "chat_read_status")
data class ChatReadStatus(
    @Id
    var id: String? = null,
    @Field("room_id")
    var roomId: String,
    @Field("member_id")
    var memberId: Long,
    @Field("last_read_message_id")
    var lastReadMessageId: String? = null,
    @Field("last_message_time")
    var lastReadMessageTime: LocalDateTime? = null
) : BaseEntity()