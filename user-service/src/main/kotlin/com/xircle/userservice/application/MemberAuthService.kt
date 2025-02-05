package com.xircle.userservice.application

import com.xircle.common.exception.NotFoundException
import com.xircle.common.response.BaseResponseStatus
import com.xircle.userservice.application.dto.MemberDto
import com.xircle.common.event.UserCreationEventDto
import com.xircle.common.util.StringUtil.Companion.CREATE_USER_TOPIC
import com.xircle.userservice.domain.integration.reader.MemberReader
import com.xircle.userservice.domain.integration.store.MemberStore
import com.xircle.userservice.domain.model.Interest
import com.xircle.userservice.domain.model.Member
import com.xircle.userservice.domain.model.MemberRole
import com.xircle.userservice.infrastructure.publisher.MessagePublisher
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberAuthService(
    private val memberStore: MemberStore,
    private val memberReader: MemberReader,
    private val passwordEncoder: PasswordEncoder,
    private val messagePublisher: MessagePublisher
) {
    @Transactional
    fun signUp(memberDto: MemberDto) {
        memberReader.isExistMemberByEmail(memberDto.email)

        val encodedPassword: String = passwordEncoder.encode(memberDto.password)
        val member = Member(
            age = memberDto.age,
            modifier = memberDto.modifier,
            email = memberDto.email,
            password = encodedPassword,
            gender = memberDto.gender,
            profileImage = "",
            introduction = memberDto.introduction,
            job = memberDto.job,
            nickname = memberDto.nickname,
            address = memberDto.address,
            university = memberDto.university,
            isProfilePublic = memberDto.isProfilePublic,
            isGraduate = memberDto.isGraduate,
            phoneNumber = memberDto.phoneNumber,
            workPlace = memberDto.workPlace,
            resume = memberDto.resume,
            isLocationPublic = memberDto.isLocationPublic,
            latitude = memberDto.latitude,
            longitude = memberDto.longitude,
            role = MemberRole.MEMBER,
        )
        memberDto.interestList.forEach {
            member.addInterest(Interest(it))
        }
        memberStore.saveMember(member)
        messagePublisher.publish(CREATE_USER_TOPIC, UserCreationEventDto(member.id!!))
    }

    @Transactional
    fun login(email: String, password: String): Member {
        val member = memberReader.findMemberByEmail(email)
        if (!passwordEncoder.matches(password, member.password)) {
            throw NotFoundException(BaseResponseStatus.NOT_EQUAL_PASSWORD)
        }
        return member
    }
}