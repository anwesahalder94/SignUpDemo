package com.example.formapplication.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.formapplication.data.db.dao.UserDao
import com.example.formapplication.data.db.entity.UserEntity

/**
 * Database is a holder class that uses annotation to define the list of entities and database version.
 */
@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java, "user.db")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}