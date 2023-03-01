package com.example.capstoneproject.viewmodel

import androidx.lifecycle.*
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.capstoneproject.data.Login
import com.example.capstoneproject.data.LoginDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Shared [ViewModel] to provide data to the [LoginListFragment], [LoginDetailFragment],
 * and [AddLoginFragment] and allow for interaction the the [LoginDao]
 */

class LoginViewModel(private val loginDao: LoginDao): ViewModel() {
    val logins: LiveData<List<Login>> = loginDao.getLogins().asLiveData()


    //gets a login using ID
    //val getLogin: LiveData<Login> = {id: Long -> loginDao.getLogin(id)} as LiveData<Login>
    fun getLogin(id: Long): LiveData<Login> {
        return loginDao.getLogin(id).asLiveData()
    }

    fun addLogin(
        id: Long,
        username: String,
        password: String,
        points: Int
    ) {
        val login = Login(
            id = id,
            username = username,
            password = password,
            points = points
        )

        viewModelScope.launch(Dispatchers.IO) {
            loginDao.insert(login)
        }

    }

    fun updateLogin(
        id: Long,
        username: String,
        password: String,
        points: Int
    ) {
        val login = Login(
            id = id,
            username = username,
            password = password,
            points = points
        )

        viewModelScope.launch(Dispatchers.IO) {
            loginDao.update(login)
        }
    }

    fun deleteLogin(login: Login) {
        viewModelScope.launch(Dispatchers.IO) {
            loginDao.delete(login)
        }
    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }
}

class LoginViewModelFactory(val loginDao: LoginDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
