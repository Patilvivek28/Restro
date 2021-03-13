package com.si.restro.ui.entry

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.si.restro.BaseViewModel
import com.si.restro.data.RetroDatabase
import com.si.restro.data.RetroRepository
import com.si.restro.data.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : BaseViewModel(application) {

    internal val isValidLogin = MutableLiveData<Boolean>()
    internal val currentUser = MutableLiveData<Users>()

    private val retroDatabase = RetroDatabase.getAppDatabase(application)
    private val retroRepository =
        RetroRepository.getInstance(retroDatabase.userDao(), retroDatabase.restaurantsDao())

    internal fun createUser(name: String, email: String, password: String) {
        launch {
            retroRepository.insertUser(name, email, password)
        }
    }

    internal fun isValidLogin(email: String, password: String) {
        launch {
            isValidLogin.value = retroRepository.isValidAccount(email, password)
        }
    }

    internal fun getUser(email: String) {
        launch {
            currentUser.value = retroRepository.getUser(email)
        }
    }
}