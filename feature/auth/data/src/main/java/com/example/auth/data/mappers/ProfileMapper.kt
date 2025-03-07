package com.example.auth.data.mappers

import com.example.auth.domain.model.Profile
import com.example.schema.RefreshTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation

fun SignInWithEmailMutation.Profile.toDomain(): Profile = Profile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)

fun SignInWithPhoneMutation.Profile.toDomain(): Profile = Profile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)

fun SignInWithGoogleMutation.Profile.toDomain(): Profile = Profile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)

fun RefreshTokenMutation.Profile.toDomain(): Profile = Profile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)