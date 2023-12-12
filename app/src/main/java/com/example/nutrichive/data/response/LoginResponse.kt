package com.example.nutrichive.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("loginResultState")
    val loginResultState: LoginResultState,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginResultState(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)