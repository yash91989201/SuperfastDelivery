package com.example.auth.data.mappers

import com.example.auth.domain.model.Auth
import com.example.schema.RefreshTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation
import com.example.core.app_state.Auth as ProtoAuth
import com.example.core.app_state.models.Auth as StoreAuth

fun SignInWithEmailMutation.Auth.toDomain() = Auth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toDomain()
)

fun SignInWithPhoneMutation.Auth.toDomain() = Auth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toDomain()
)

fun SignInWithGoogleMutation.Auth.toDomain() = Auth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toDomain()
)

fun RefreshTokenMutation.Auth.toDomain() = Auth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toDomain()
)

fun ProtoAuth.toStore() = StoreAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.emailVerified,
    phone = this.phone,
    authRole = this.authRole.toStore(),
)
