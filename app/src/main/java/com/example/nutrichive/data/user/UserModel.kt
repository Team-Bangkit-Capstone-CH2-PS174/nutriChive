package com.example.nutrichive.data.user

data class UserModel(
    val token: String? = null,
    val name: String? = null,
    val username: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val isLogin: Boolean = false
)