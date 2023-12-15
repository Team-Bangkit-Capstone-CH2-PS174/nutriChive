package com.example.nutrichive.data.user

import android.util.Log
import androidx.lifecycle.liveData
import com.example.nutrichive.data.api.ApiService
import com.example.nutrichive.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject

class UserRepository private constructor(private val  apiService: ApiService, private val userPreference: UserPreference) {

    fun register(name: String, email: String, password: String, username: String, phoneNumber: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.register(name, email, password, username, phoneNumber).execute()
            }
            if (response.isSuccessful) {
                emit(ResultState.Success(response.body()))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "{\"error\":1,\"message\":\"email sudah terdaptar\"}"
                try {
                    val errorJson = JSONObject(errorMessage)
                    val error = errorJson.optBoolean("error", false)
                    val message = errorJson.optString("message")

                    if (error) {
                        emit(ResultState.Error(message))
                    } else {
                        emit(ResultState.Error("An error occurred."))
                    }
                } catch (e: JSONException) {
                    emit(ResultState.Error("An error occurred."))
                }
                Log.e("register", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("register", "onFailure: ${e.message}")
        }
    }

    fun login(email: String, password: String)= liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.login(email, password).execute()
            }
            if (response.isSuccessful) {
                emit(ResultState.Success(response.body()))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "{\"error\":1,\"message\":\"email sudah terdaptar.\"}"
                try {
                    val errorJson = JSONObject(errorMessage)
                    val error = errorJson.optBoolean("error", false)
                    val message = errorJson.optString("message")

                    if (error) {
                        emit(ResultState.Error(message))
                    } else {
                        emit(ResultState.Error("An error occurred."))
                    }
                } catch (e: JSONException) {
                    emit(ResultState.Error("An error occurred."))
                }
                Log.e("register", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("register", "onFailure: ${e.message}")
        }
    }

    fun addFavorite(token: String?, idRecipe: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.addFavorite("Bearer $token", idRecipe).execute()
            }
            if (response.isSuccessful) {
                emit(ResultState.Success(response.body()))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "{\"error\":1,\"message\":\"email sudah terdaptar\"}"
                try {
                    val errorJson = JSONObject(errorMessage)
                    val error = errorJson.optBoolean("error", false)
                    val message = errorJson.optString("message")

                    if (error) {
                        emit(ResultState.Error(message))
                    } else {
                        emit(ResultState.Error("An error occurred."))
                    }
                } catch (e: JSONException) {
                    emit(ResultState.Error("An error occurred."))
                }
                Log.e("favorite", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("favorite", "onFailure: ${e.message}")
        }
    }

    fun dropFavorite(token: String?, idRecipe: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.dropFavorite("Bearer $token", idRecipe).execute()
            }
            if (response.isSuccessful) {
                emit(ResultState.Success(response.body()))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "{\"error\":1,\"message\":\"email sudah terdaptar\"}"
                try {
                    val errorJson = JSONObject(errorMessage)
                    val error = errorJson.optBoolean("error", false)
                    val message = errorJson.optString("message")

                    if (error) {
                        emit(ResultState.Error(message))
                    } else {
                        emit(ResultState.Error("An error occurred."))
                    }
                } catch (e: JSONException) {
                    emit(ResultState.Error("An error occurred."))
                }
                Log.e("unFavorite", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("unFavorite", "onFailure: ${e.message}")
        }
    }

    fun searchFav(token: String?, idRecipe: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.searchFav("Bearer $token", idRecipe).execute()
            }
            if (response.isSuccessful) {
                emit(ResultState.Success(response.body()?.data))
            } else {
                emit(ResultState.Error("Data Gagal Dimuat"))
                Log.d("Search Favorite", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d("Search Favorite", "onFailure: ${e.message}")
        }
    }

    fun getListFavorite(token: String?) = liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.getListFavorite("Bearer $token").execute()
            }
            if (response.isSuccessful){
                emit(ResultState.Success(response.body()?.data))
            } else {
                emit(ResultState.Error("Data Gagal Dimuat"))
                Log.d("All Recipes", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d("All Recipes", "onFailure: ${e.message}")
        }
    }

    suspend fun setSession(user: UserModel) {
        userPreference.setSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}