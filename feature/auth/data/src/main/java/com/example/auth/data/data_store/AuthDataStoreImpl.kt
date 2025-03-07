package com.example.auth.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.auth.data.mappers.toStore
import com.example.auth.data.mappers.toProto
import com.example.common.Auth
import com.example.common.data_store.AuthDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import com.example.common.models.Auth as StoreAuth
import com.example.common.models.AuthRole as StoreAuthRole

private val Context.authDataStore: DataStore<Auth> by dataStore(
    fileName = "auth.pb",
    serializer = AuthSerializer
)

class AuthDataStoreImpl @Inject constructor(@ApplicationContext private val context: Context) :
    AuthDataStore {
    override suspend fun saveAuth(
        id: String,
        email: String?,
        emailVerified: Boolean,
        phone: String?,
        authRole: StoreAuthRole
    ) {
        context.authDataStore.updateData { currentAuth ->
            currentAuth.toBuilder()
                .setId(id)
                .apply {
                    email?.let { setEmail(it) }
                    phone?.let { setPhone(it) }
                }
                .setEmailVerified(emailVerified)
                .setAuthRole(authRole.toProto())
                .build()
        }
    }

    override fun getAuth(): Flow<StoreAuth> {
        return context.authDataStore.data.map { it.toStore() }
    }

    override suspend fun clearAuth() {
        context.authDataStore.updateData {
            it.toBuilder().clear().build()
        }
    }
}

object AuthSerializer : Serializer<Auth> {
    override val defaultValue: Auth = Auth.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Auth {
        return Auth.parseFrom(input)
    }

    override suspend fun writeTo(t: Auth, output: OutputStream) {
        t.writeTo(output)
    }
}