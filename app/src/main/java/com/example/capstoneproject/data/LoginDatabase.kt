package com.example.capstoneproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Login::class], version = 1, exportSchema = false)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun LoginDao(): LoginDao


    companion object {
        @Volatile
        private var INSTANCE: LoginDatabase? = null

        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                LoginDatabase::class.java,
                "login_database"
            ).build().apply {
                INSTANCE = this
            }
        }
    }
}