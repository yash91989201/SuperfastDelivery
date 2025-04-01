package com.example.account.data.mappers

import com.apollographql.apollo.api.Optional
import com.example.schema.CreateProfileMutation
import com.example.schema.UpdateProfileMutation
import com.example.account.domain.model.CreateProfileInput as DomainCreateProfileInput
import com.example.account.domain.model.Profile as DomainProfile
import com.example.account.domain.model.UpdateProfileInput as DomainUpdateProfileInput
import com.example.core.app_state.Profile as ProtoProfile
import com.example.core.app_state.models.Profile as StoreProfile
import com.example.schema.type.CreateProfileInput as SchemaCreateProfileInput
import com.example.schema.type.UpdateProfileInput as SchemaUpdateProfileInput

fun DomainCreateProfileInput.toSchema() = SchemaCreateProfileInput(
    name = this.name,
    image_url = Optional.presentIfNotNull(this.imageUrl),
    dob = Optional.presentIfNotNull(this.dob),
    anniversary = Optional.presentIfNotNull(this.anniversary),
    gender = Optional.presentIfNotNull(this.gender?.toSchema()),
    auth_id = this.authId
)

fun DomainUpdateProfileInput.toSchema() = SchemaUpdateProfileInput(
    id = this.id,
    name = Optional.presentIfNotNull(this.name),
    image_url = Optional.presentIfNotNull(this.imageUrl),
    dob = Optional.presentIfNotNull(this.dob),
    anniversary = Optional.presentIfNotNull(this.anniversary),
    gender = Optional.presentIfNotNull(this.gender.toSchema()),
    auth_id = this.authId
)

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


fun UpdateProfileMutation.UpdateProfile.toDomain() = DomainProfile(
    id = this.id,
    name = this.name,
    imageUrl = this.image_url,
    dob = this.dob,
    anniversary = this.anniversary,
    gender = this.gender?.toDomain(),
    authId = this.auth_id
)

