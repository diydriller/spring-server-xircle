package com.xircle.userservice.infrastructure.repository

import com.xircle.userservice.domain.model.Member
import org.springframework.data.jpa.domain.Specification

class MemberSpecification {
    companion object {
        fun equalAge(age: Int?): Specification<Member>? = age?.let {
            return Specification { root, _, cb ->
                cb.equal(root.get<Int>("age"), age)
            }
        }

        fun equalUniversity(university: String?): Specification<Member>? = university?.let {
            return Specification { root, _, cb ->
                cb.equal(root.get<String>("university"), university)
            }
        }

        fun equalGender(gender: String?): Specification<Member>? = gender?.let {
            return Specification { root, _, cb ->
                cb.equal(root.get<String>("gender"), gender)
            }
        }

        fun notEqualId(id: Long): Specification<Member> {
            return Specification { root, _, cb ->
                cb.notEqual(root.get<Long>("id"), id)
            }
        }
    }
}