package com.example.common.models

import java.time.Instant

data class SessionData(
    val accessToken: String,
    val accessTokenExpiresAt: Instant,
    val sessionId: String
)