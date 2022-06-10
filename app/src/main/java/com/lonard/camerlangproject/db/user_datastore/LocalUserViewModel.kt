package com.lonard.camerlangproject.db.user_datastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocalUserViewModel(private val localUserPref: LocalUser_pref): ViewModel() {

    fun getLocalUser(): LiveData<UserModel> {
        return localUserPref.getUserDataLocal().asLiveData()
    }

    fun getStartUp(): LiveData<AppSettingModel>  {
        return localUserPref.getStartUp().asLiveData()
    }

    fun setDarkTheme(darkThemeSet: Boolean) {
        viewModelScope.launch {
            localUserPref.saveDarkTheme(darkThemeSet)
        }
    }

    fun disableFirstRun() {
        viewModelScope.launch {
            localUserPref.disableFirstRunInfo()
        }
    }

    fun modifyLocalUser(localUserAccount: UserModel) {
        viewModelScope.launch {
            localUserPref.saveUserDataLocal(localUserAccount)
        }
    }

}