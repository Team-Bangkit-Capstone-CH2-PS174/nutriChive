package com.example.nutrichive.data.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun setSession(user: UserModel){
        dataStore.edit { preference ->
            preference[TOKEN_KEY] = user.token ?: ""
            preference[NAME_KEY] = user.name ?: ""
            preference[USERNAME_KEY] = user.username ?: ""
            preference[PHONENUMBER_KEY] = user.phoneNumber ?: ""
            preference[EMAIL_KEY] = user.email ?: ""
            preference[LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preference ->
            UserModel(
                preference[TOKEN_KEY] ?: "",
                preference[NAME_KEY] ?: "",
                preference[USERNAME_KEY] ?: "",
                preference[PHONENUMBER_KEY] ?: "",
                preference[EMAIL_KEY] ?: "",
                preference[LOGIN_KEY] ?: false
            )
        }
    }
    suspend fun logout(){
        dataStore.edit { preference ->
            preference.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PHONENUMBER_KEY = stringPreferencesKey("phoneNumber")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}