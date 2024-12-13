package com.xircle.chatservice.domain.model

import jakarta.persistence.*

@Entity
class ChatRoomMember(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_member_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    var chatRoom: ChatRoom? = null,

    val memberId: Long,

    val isHost: Boolean,

    @Enumerated(EnumType.STRING)
    var entryStatus: EntryStatus = EntryStatus.NOT_ENTERED
) : BaseEntity() {
    fun setChatRoom(chatRoom: ChatRoom) {
        this.chatRoom = chatRoom
    }
}