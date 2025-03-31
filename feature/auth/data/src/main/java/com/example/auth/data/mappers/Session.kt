package com.example.auth.data.mappers

import com.example.schema.RefreshAccessTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation
import com.example.auth.domain.model.Session as DomainSession
import com.example.core.app_state.models.Session as StoreSession

fun SignInWithEmailMutation.Session.toDomain() = DomainSession(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun SignInWithPhoneMutation.Session.toDomain() = DomainSession(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun SignInWithGoogleMutation.Session.toDomain() = DomainSession(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun RefreshAccessTokenMutation.Session.toDomain() = DomainSession(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun SignInWithEmailMutation.Session.toStore() = StoreSession(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun SignInWithPhoneMutation.Session.toStore() = StoreSession(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun SignInWithGoogleMutation.Session.toStore() = StoreSession(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun RefreshAccessTokenMutation.Session.toStore() = StoreSession(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)