package com.example.auth.data.mappers

import com.example.schema.RefreshAccessTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation
import com.example.auth.domain.model.Auth as DomainAuth
import com.example.core.app_state.Auth as ProtoAuth
import com.example.core.app_state.models.Auth as StoreAuth

fun SignInWithEmailMutation.Auth.toDomain() = DomainAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toDomain()
)

fun SignInWithPhoneMutation.Auth.toDomain() = DomainAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toDomain()
)

fun SignInWithGoogleMutation.Auth.toDomain() = DomainAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toDomain()
)

fun RefreshAccessTokenMutation.Auth.toDomain() = DomainAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toDomain()
)

fun SignInWithEmailMutation.Auth.toStore() = StoreAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toStore()
)

fun SignInWithPhoneMutation.Auth.toStore() = StoreAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toStore()
)

fun SignInWithGoogleMutation.Auth.toStore() = StoreAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toStore()
)

fun RefreshAccessTokenMutation.Auth.toStore() = StoreAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.email_verified,
    phone = this.phone,
    authRole = this.auth_role.toStore()
)

fun ProtoAuth.toStore() = StoreAuth(
    id = this.id,
    email = this.email,
    emailVerified = this.emailVerified,
    phone = this.phone,
    authRole = this.authRole.toStore(),
)
