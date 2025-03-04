package com.example.common.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.*
import java.util.*

enum class AuthRole {
    CUSTOMER, DELIVERY_PARTNER, VENDOR, ADMIN
}

class Converters {
    @TypeConverter
    fun fromAuthRole(value: AuthRole): String {
        return value.name
    }

    @TypeConverter
    fun toAuthRole(value: String): AuthRole {
        return AuthRole.valueOf(value)
    }
}


@Entity(
    tableName = "auth",
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["phone"], unique = true)
    ]
)
data class AuthEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "email")
    val email: String? = null,

    @ColumnInfo(name = "email_verified", defaultValue = "0")
    val emailVerified: Boolean = false,

    @ColumnInfo(name = "phone")
    val phone: String? = null,

    @ColumnInfo(name = "auth_role")
    val authRole: AuthRole,

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP")
    val updatedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "deleted_at")
    val deletedAt: Long? = null
)
