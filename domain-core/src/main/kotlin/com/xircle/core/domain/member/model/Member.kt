package com.xircle.core.domain.member.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long?,
    var age: Int,
    var modifier: String,
    var email: String,
    var password: String,
    var gender: String,
    @Column(name = "profile_image")
    private var profileImage: String?,
    var introduction: String?,
    var job: String?,
    var nickname: String,
    var address: String,
    var university: String,
    @Column(name = "is_public")
    var isPublic: Boolean,
    @Column(name = "is_graduate")
    var isGraduate: Boolean,
    var phoneNumber: String,
    @Column(name = "work_place")
    var workPlace: String?,
    var resume: String?,
    @Column(name = "is_location_public")
    var isLocationPublic: Boolean,
    var longitude: Double?,
    var latitude: Double?
)