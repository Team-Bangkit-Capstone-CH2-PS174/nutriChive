package com.example.nutrichive.ui.register

import androidx.lifecycle.ViewModel
import com.example.nutrichive.data.user.UserRepository

class RegisterViewModel (private val userRepository: UserRepository): ViewModel() {
    fun registerUser(name: String, email: String, password: String, username: String, phoneNumber: String) =
        userRepository.register(name, email, password, username, phoneNumber)

}