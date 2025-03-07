package com.example.account.data.mappers

import com.example.schema.CreateProfileMutation
import com.example.account.domain.model.Profile as DomainProfile
import com.example.common.Profile as ProtoProfile
import com.example.common.models.Profile as StoreProfile

fun ProtoProfile.toStore() = StoreProfile(
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl,
    dob = this.dob.toStore(),
    anniversary = this.anniversary.toStore(),
    gender = this.gender.toStore(),
    authId = this.authId
)

fun StoreProfile.toDomain() = DomainProfile(
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.authId
)

fun CreateProfileMutation.CreateProfile.toDomain() = DomainProfile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)
