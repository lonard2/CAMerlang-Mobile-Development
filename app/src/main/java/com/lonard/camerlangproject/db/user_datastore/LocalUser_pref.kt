package com.lonard.camerlangproject.db.user_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUser_pref private constructor(private val ds: DataStore<Preferences>) {

    fun getUserDataLocal(): Flow<UserModel> {
        return ds.data.map { userPref ->
            UserModel(
                userPref[NAME_PREF_KEY] ?: "Anonymous",
                userPref[AGE_PREF_KEY] ?: 0,
                userPref[PROFESSION_PREF_KEY] ?: "None",
                userPref[STATUS_PREF_KEY] ?: "",
            )
        }
    }

    suspend fun saveUserDataLocal(userAccount: UserModel) {
        ds.edit { userPref ->
            userPref[NAME_PREF_KEY] = userAccount.name
            userPref[AGE_PREF_KEY] = userAccount.age
            userPref[PROFESSION_PREF_KEY] = userAccount.profession
            userPref[STATUS_PREF_KEY] = userAccount.status
        }
    }

    companion object {
        private val NAME_PREF_KEY = stringPreferencesKey("camerlang_user_name")
        private val AGE_PREF_KEY = intPreferencesKey("camerlang_user_age")
        private val PROFESSION_PREF_KEY = stringPreferencesKey("camerlang_user_profession")
        private val STATUS_PREF_KEY = stringPreferencesKey("camerlang_user_status")

        @Volatile
        private var INSTANCE: LocalUser_pref? = null

        fun getLocalUserInstance(dataStore: DataStore<Preferences>): LocalUser_pref {
            return INSTANCE ?: synchronized(this) {
                val instance = LocalUser_pref(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}