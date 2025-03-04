package com.example.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.common.dao.AuthDAO
import com.example.common.dao.ProfileDAO
import com.example.common.entities.AuthEntity
import com.example.common.entities.ProfileEntity

@Database(entities = [AuthEntity::class, ProfileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDAO(): AuthDAO
    abstract fun profileDAO(): ProfileDAO
}