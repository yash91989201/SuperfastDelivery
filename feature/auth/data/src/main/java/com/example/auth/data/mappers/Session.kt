package com.example.auth.data.mappers

import com.example.auth.domain.model.Session
import com.example.schema.RefreshTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation

fun SignInWithEmailMutation.Session.toDomain() = Session(
    id = this.id,
    accessToken = this.access_token,
    accessTokenExpiresAt = this.access_token_expires_at
)

fun SignInWithPhoneMutation.Session.toDomain() = Session(
    id = this.id,
    accessToken = this.access_token,
    accessTokenExpiresAt = this.access_token_expires_at
)

fun SignInWithGoogleMutation.Session.toDomain() = Session(
    id = this.id,
    accessToken = this.access_token,
    accessTokenExpiresAt = this.access_token_expires_at
)

fun RefreshTokenMutation.Session.toDomain() = Session(
    id = this.id,
    accessToken = this.access_token,
    accessTokenExpiresAt = this.access_token_expires_at
)