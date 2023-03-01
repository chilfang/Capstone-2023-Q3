package com.example.capstoneproject

import android.app.Application
import com.example.capstoneproject.data.LoginDatabase

/**
 * An application class that inherits from [Application], allows for the creation of a singleton
 * instance of the [LoginDatabase]
 */
class BaseApplication : Application() {
    val database: LoginDatabase by lazy { LoginDatabase.getDatabase(this) }
}