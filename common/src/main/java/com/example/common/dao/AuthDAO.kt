package com.example.common.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.common.entities.AuthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthDAO {

    @Query("SELECT * FROM auth")
    fun getAllAuthUsers(): Flow<List<AuthEntity>>

    @Query("SELECT * FROM auth WHERE id = :id")
    suspend fun getAuthUserById(id: String): AuthEntity?

    @Query("SELECT * FROM auth WHERE email = :email")
    suspend fun getAuthUserByEmail(email: String): AuthEntity?

    @Query("SELECT * FROM auth WHERE phone = :phone")
    suspend fun getAuthUserByPhone(phone: String): AuthEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthUser(authEntity: AuthEntity)

    @Update
    suspend fun updateAuthUser(authEntity: AuthEntity)

    @Delete
    suspend fun deleteAuthUser(authEntity: AuthEntity)

    @Query("UPDATE auth SET deleted_at = :deletedAt WHERE id = :id")
    suspend fun softDeleteAuthUser(id: String, deletedAt: Long? = System.currentTimeMillis())

    @Query("SELECT * FROM auth WHERE deleted_at IS NULL")
    fun getActiveAuthUsers(): Flow<List<AuthEntity>>
}