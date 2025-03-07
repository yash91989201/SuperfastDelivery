package com.example.common.models

import java.time.Instant

data class Session(
    val sessionId: String,
    val accessToken: String,
    val accessTokenExpiresAt: Instant,
)