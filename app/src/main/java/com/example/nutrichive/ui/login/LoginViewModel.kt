package com.example.nutrichive.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nutrichive.data.user.UserModel
import com.example.nutrichive.data.user.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun login(email: String, password: String)= userRepository.login(email, password)

    fun setSession(user: UserModel) {
        viewModelScope.launch { userRepository.setSession(user) }
    }
    fun getSession() : LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}