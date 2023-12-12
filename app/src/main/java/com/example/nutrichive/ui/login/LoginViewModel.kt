package com.example.nutrichive.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nutrichive.data.di.Injection
import com.example.nutrichive.data.user.UserPreference
import com.example.nutrichive.data.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository, private val userPreference: UserPreference) : ViewModel() {
    fun login(email: String, password: String)= userRepository.login(email, password)
    fun register(username: String, email: String, password: String)= userRepository.register(username, email, password)
    fun saveToken(token: String){
        viewModelScope.launch (Dispatchers.IO){
            userPreference.saveToken(token)
        }
    }
    fun checkIfFirstTime(): LiveData<Boolean> {
        return userPreference.isFirstTime().asLiveData()
    }

    class LoginViewModelFactory private constructor(
        private val userRepository: UserRepository,
        private val userPreference: UserPreference
    ): ViewModelProvider.NewInstanceFactory(){
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(userRepository, userPreference) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }
        companion object {
            @Volatile
            private var instance: LoginViewModelFactory? = null
            fun getInstance(userPreference: UserPreference): LoginViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: LoginViewModelFactory(
                        Injection.provideUserRepository(),
                        userPreference
                    )
                }
        }
    }

}