package com.example.auth.data.mappers

import com.example.schema.RefreshAccessTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation
import com.example.auth.domain.model.Profile as DomainProfile

fun SignInWithEmailMutation.Profile.toDomain() = DomainProfile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)

fun SignInWithPhoneMutation.Profile.toDomain() = DomainProfile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)

fun SignInWithGoogleMutation.Profile.toDomain() = DomainProfile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)

fun RefreshAccessTokenMutation.Profile.toDomain() = DomainProfile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)