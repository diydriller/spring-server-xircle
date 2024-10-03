package com.xircle.core.domain.notification.model

import com.xircle.core.domain.common.model.BaseEntity
import com.xircle.core.domain.notification.enum.NotificationType
import jakarta.persistence.*

@Entity
class Notification(
    @Column(name = "member_id")
    private var memberId: Long? = null,
    @Enumerated(EnumType.STRING)
    private val type: NotificationType,
): BaseEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    var id: Long? = null;
}