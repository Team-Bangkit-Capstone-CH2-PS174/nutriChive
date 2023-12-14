package com.example.nutrichive.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Datalogin
)

data class Datalogin(

	@field:SerializedName("token")
	val token: String
)
