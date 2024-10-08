package com.xircle.core.domain.member.service

import com.xircle.common.exception.NotFoundException
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.domain.member.dto.MemberInfo
import com.xircle.core.domain.member.model.Interest
import com.xircle.core.domain.member.model.Member
import com.xircle.core.domain.member.model.MemberRole
import com.xircle.core.store.member.MemberStore
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberAuthService(
    private val memberStore: MemberStore,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun signUp(memberInfo: MemberInfo) {
        memberStore.isExistMemberByEmail(memberInfo.email)

        val encodedPassword: String = passwordEncoder.encode(memberInfo.password)
        val member = Member(
            age = memberInfo.age,
            modifier = memberInfo.modifier,
            email = memberInfo.email,
            password = encodedPassword,
            gender = memberInfo.gender,
            profileImage = "",
            introduction = memberInfo.introduction,
            job = memberInfo.job,
            nickname = memberInfo.nickname,
            address = memberInfo.address,
            university = memberInfo.university,
            isProfilePublic = memberInfo.isProfilePublic,
            isGraduate = memberInfo.isGraduate,
            phoneNumber = memberInfo.phoneNumber,
            workPlace = memberInfo.workPlace,
            resume = memberInfo.resume,
            isLocationPublic = memberInfo.isLocationPublic,
            latitude = memberInfo.latitude,
            longitude = memberInfo.longitude,
            role = MemberRole.MEMBER,
        )
        memberInfo.interestList.forEach {
            member.addInterest(Interest(it))
        }
        memberStore.saveMember(member)
    }

    fun login(email: String, password: String): Member {
        val member = memberStore.findMemberByEmail(email)
        if (!passwordEncoder.matches(password, member.password)) {
            throw NotFoundException(BaseResponseStatus.NOT_EQUAL_PASSWORD)
        }
        return member
    }
}