package com.example.capstoneproject.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginDao {
    @Query("SELECT * from login_database")
    fun getLogins() : Flow<List<Login>>

    @Query("SELECT * from login_database WHERE id = :id")
    fun getLogin(id: Long) : Flow<Login>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(login: Login)

    @Update
    fun update(login: Login)

    @Delete
    fun delete(login: Login)
}