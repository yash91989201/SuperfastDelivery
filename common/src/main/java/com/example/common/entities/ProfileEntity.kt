package com.example.common.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.UUID

enum class Gender {
    MALE, FEMALE, OTHERS, UNDISCLOSED
}

class ProfileConverters {
    @TypeConverter
    fun fromGender(value: Gender?): String? {
        return value?.name
    }

    @TypeConverter
    fun toGender(value: String?): Gender? {
        return value?.let { Gender.valueOf(it) }
    }
}

@Entity(
    tableName = "profile",
    foreignKeys = [
        ForeignKey(
            entity = AuthEntity::class,
            parentColumns = ["id"],
            childColumns = ["auth_id"],
            onDelete = ForeignKey.CASCADE // If an auth entry is deleted, the profile is also deleted
        )
    ],
    indices = [Index(value = ["auth_id"])]
)
data class ProfileEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "dob")
    val dob: Long? = null,

    @ColumnInfo(name = "anniversary")
    val anniversary: Long? = null,

    @ColumnInfo(name = "gender")
    val gender: Gender? = null,

    @ColumnInfo(name = "auth_id")
    val authId: String,

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP")
    val updatedAt: Long = System.currentTimeMillis()
)