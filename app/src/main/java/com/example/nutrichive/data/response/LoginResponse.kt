package com.example.nutrichive.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Datalogin
)

data class Datalogin(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("__v")
	val v: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)