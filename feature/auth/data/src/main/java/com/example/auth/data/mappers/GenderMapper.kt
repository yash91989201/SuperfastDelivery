package com.example.auth.data.mappers

import com.example.schema.type.Gender
import com.example.auth.domain.model.Gender as DomainGender

fun Gender.toDomain(): DomainGender {
    return when (this) {
        Gender.MALE -> DomainGender.MALE
        Gender.FEMALE -> DomainGender.FEMALE
        Gender.OTHERS -> DomainGender.OTHERS
        Gender.UNDISCLOSED -> DomainGender.UNDISCLOSED
        Gender.UNKNOWN__ -> DomainGender.UNDISCLOSED
    }
}