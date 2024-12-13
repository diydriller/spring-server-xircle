package com.xircle.chatservice.domain.model

import jakarta.persistence.*

@Entity
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    var id: Long? = null,

    @OneToMany(cascade = [CascadeType.PERSIST])
    val chatRoomMemberList: MutableList<ChatRoomMember> = mutableListOf()
) : BaseEntity() {
    fun addChatRoomMember(chatRoomMember: ChatRoomMember) {
        this.chatRoomMemberList.add(chatRoomMember)
        chatRoomMember.setChatRoom(this)
    }
}