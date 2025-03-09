package com.xircle.chatservice.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "chat_room")
data class ChatRoom(
    @Id
    var id: String? = null,

    var name: String,

    @Field("member_list")
    val memberList: MutableList<MemberInfo> = mutableListOf()
) : BaseEntity() {
    fun addMember(memberInfo: MemberInfo) {
        this.memberList.add(memberInfo)
    }
}

data class MemberInfo(
    val id: Long,
    val isHost: Boolean
)