package com.xircle.chatservice.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "chat_member")
data class ChatMember(
    @Id
    var id: String? = null,
    @Field("member_id")
    val memberId: Long?,
    @Field("room_id")
    val roomId: String,
    @Field("is_host")
    val isHost: Boolean
) : BaseEntity()
