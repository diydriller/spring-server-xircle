package com.xircle.userservice.domain.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
class Member(
    var age: Int,
    var modifier: String,
    var email: String,
    var password: String,
    var gender: String,
    @Column(name = "profile_image")
    var profileImage: String,
    var introduction: String,
    var job: String,
    var nickname: String,
    var address: String,
    var university: String,
    @Column(name = "is_profile_public")
    var isProfilePublic: Boolean,
    @Column(name = "is_graduate")
    var isGraduate: Boolean,
    var phoneNumber: String,
    @Column(name = "work_place")
    var workPlace: String?,
    var resume: String?,
    @Column(name = "is_location_public")
    var isLocationPublic: Boolean,
    var longitude: Double?,
    var latitude: Double?,
    @Enumerated(EnumType.STRING)
    var role: MemberRole
) : BaseEntity(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null

    @OneToMany(mappedBy = "member", cascade = [CascadeType.PERSIST])
    var interestList: MutableList<Interest> = ArrayList()

    fun addInterest(interest: Interest) {
        interestList.add(interest)
        interest.member = this
    }

    companion object {
        const val SERIAL_VERSION_UID: Long = 1L
    }
}