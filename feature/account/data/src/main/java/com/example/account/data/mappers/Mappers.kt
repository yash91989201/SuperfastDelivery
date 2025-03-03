package com.example.account.data.mappers

import com.example.schema.type.Gender
import com.example.account.domain.model.Profile
import com.example.schema.CreateProfileMutation

fun Gender.toDomain(): com.example.account.domain.model.Gender {
    return when (this) {
        Gender.MALE -> com.example.account.domain.model.Gender.MALE
        Gender.FEMALE -> com.example.account.domain.model.Gender.FEMALE
        Gender.OTHERS -> com.example.account.domain.model.Gender.OTHERS
        Gender.UNDISCLOSED -> com.example.account.domain.model.Gender.UNDISCLOSED
        Gender.UNKNOWN__ -> TODO()
    }
}

fun com.example.account.domain.model.Gender.fromDomain(): Gender{
 return when(this){
     com.example.account.domain.model.Gender.MALE -> Gender.MALE
     com.example.account.domain.model.Gender.FEMALE -> Gender.FEMALE
     com.example.account.domain.model.Gender.OTHERS -> Gender.OTHERS
     com.example.account.domain.model.Gender.UNDISCLOSED -> Gender.UNDISCLOSED
 }
}

fun CreateProfileMutation.CreateProfile.toDomain(): Profile{
    return Profile(
        id = this.id,
        name = this.name,
        imageUrl = this.image_url,
        dob = this.dob,
        anniversary = this.anniversary,
        gender = this.gender?.toDomain(),
        authId = this.auth_id
    )
}