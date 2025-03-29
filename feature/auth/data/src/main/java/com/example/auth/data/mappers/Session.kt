package com.example.auth.data.mappers

import com.example.auth.domain.model.Session
import com.example.schema.RefreshAccessTokenMutation
import com.example.schema.SignInWithEmailMutation
import com.example.schema.SignInWithGoogleMutation
import com.example.schema.SignInWithPhoneMutation

fun SignInWithEmailMutation.Session.toDomain() = Session(

    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun SignInWithPhoneMutation.Session.toDomain() = Session(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun SignInWithGoogleMutation.Session.toDomain() = Session(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)

fun RefreshAccessTokenMutation.Session.toDomain() = Session(
    accessToken = this.access_token,
    refreshToken = this.refresh_token,
)