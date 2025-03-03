package com.example.account.domain.repository

import com.example.account.domain.model.CreateProfileInput
import com.example.account.domain.model.Profile

interface AccountRepository{
    suspend fun createProfile(newProfile: CreateProfileInput): Result<Profile>
    suspend fun updateProfile(profile: Profile): Result<Profile>
}