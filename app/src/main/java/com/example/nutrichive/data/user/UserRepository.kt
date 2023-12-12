package com.example.nutrichive.data.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.nutrichive.data.api.ApiService
import com.example.nutrichive.utils.ResultState
import com.example.nutrichive.data.response.LoginResponse
import com.example.nutrichive.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(private val  apiService: ApiService) {

    private val loginResultState = MediatorLiveData<ResultState<LoginResponse>>()
    private val registerResultState = MediatorLiveData<ResultState<RegisterResponse>>()

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<ResultState<RegisterResponse>> {
        registerResultState.value = ResultState.Loading
        val client = apiService.register(
            name,
            email,
            password
        )

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerInfo = response.body()
                    if (registerInfo != null) {
                        registerResultState.value = ResultState.Success(registerInfo)
                    } else {
                        registerResultState.value = ResultState.Error(REGISTER_ERROR_MESSAGE)
                        Log.e(TAG, "Failed: Register Info is null")
                    }
                } else {
                    registerResultState.value = ResultState.Error(REGISTER_ERROR_MESSAGE)
                    Log.e(TAG, "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerResultState.value = ResultState.Error(REGISTER_ERROR_MESSAGE)
                Log.e(TAG, "Failed: Response Failure - ${t.message.toString()}")
            }

        })

        return registerResultState
    }

    fun login(
        email: String,
        password: String
    ): LiveData<ResultState<LoginResponse>> {
        loginResultState.value = ResultState.Loading
        val client = apiService.login(
            email,
            password
        )

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginInfo = response.body()
                    if (loginInfo != null) {
                        loginResultState.value = ResultState.Success(loginInfo)
                    } else {
                        loginResultState.value = ResultState.Error(LOGIN_ERROR_MESSAGE)
                        Log.e(TAG, "Failed: Login Info is null")
                    }
                } else {
                    loginResultState.value = ResultState.Error(LOGIN_ERROR_MESSAGE)
                    Log.e(TAG, "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResultState.value = ResultState.Error(LOGIN_ERROR_MESSAGE)
                Log.e(TAG, "Failed: Response Failure - ${t.message.toString()}")
            }
        })

        return loginResultState
    }

    companion object {
        private val TAG = UserRepository::class.java.simpleName
        private const val LOGIN_ERROR_MESSAGE = "Login failed, please try again."
        private const val REGISTER_ERROR_MESSAGE = "Register failed, please try again."

        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}