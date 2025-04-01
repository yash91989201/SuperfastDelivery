package com.example.account.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.account.data.mappers.toProto
import com.example.account.data.mappers.toStore
import com.example.core.app_state.Date
import com.example.core.app_state.Profile
import com.example.core.app_state.data_store.ProfileDataStore
import com.example.core.app_state.models.Gender
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream
import java.time.LocalDate
import javax.inject.Inject
import com.example.core.app_state.models.Profile as StoreProfile

private val Context.profileDataStore: DataStore<Profile> by dataStore(
    fileName = "profile.pb",
    serializer = ProfileSerializer
)

class ProfileDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ProfileDataStore {
    override suspend fun saveProfile(
        id: String,
        name: String,
        imageUrl: String?,
        dob: LocalDate?,
        anniversary: LocalDate?,
        gender: Gender?,
        authId: String
    ) {
        context.profileDataStore.updateData { currentProfile ->
            currentProfile
                .toBuilder()
                .apply {
                    imageUrl?.let { setImageUrl(it) }
                    dob?.let {
                        Date.newBuilder()
                            .setYear(it.year)
                            .setMonth(it.monthValue)
                            .setDay(it.dayOfMonth)
                            .build()
                    }

                    anniversary?.let {
                        Date.newBuilder()
                            .setYear(it.year)
                            .setMonth(it.monthValue)
                            .setDay(it.dayOfMonth)
                            .build()
                    }

                    gender?.let { setGender(it.toProto()) }
                }
                .setId(id)
                .setName(name)
                .setAuthId(authId)
                .build()
        }
    }

    override fun getProfile(): Flow<StoreProfile?> {
        return context.profileDataStore.data.map { it.toStore() }
    }

    override suspend fun clearProfile() {
        context.profileDataStore.updateData {
            it.toBuilder().clear().build()
        }
    }
}

object ProfileSerializer : Serializer<Profile> {
    override val defaultValue: Profile = Profile.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Profile {
        return Profile.parseFrom(input)
    }

    override suspend fun writeTo(t: Profile, output: OutputStream) {
        t.writeTo(output)
    }
}