package com.lonard.camerlangproject.db.user_datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LocalUserViewModelFactory (private val localUserPref: LocalUser_pref):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LocalUserViewModel::class.java)) {
            return LocalUserViewModel(localUserPref) as T
        }

        throw IllegalArgumentException("The view model of local user account cannot be found. " +
                "Please check or recheck it in your project files!")
    }
}