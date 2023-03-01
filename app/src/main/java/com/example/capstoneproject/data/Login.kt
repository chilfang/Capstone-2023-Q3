package com.example.capstoneproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_database")
data class Login (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val password: String,
    val points: Int
)