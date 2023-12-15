package com.example.nutrichive.data.response

import com.google.gson.annotations.SerializedName

data class ListFavoriteResponse(

	@field:SerializedName("data")
	val data: List<DataItem2>,

	@field:SerializedName("message")
	val message: String
)

data class IngredientsItem2(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("name")
	val name: String
)

data class User(

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

data class DataItem2(

	@field:SerializedName("product")
	val product: Product,

	@field:SerializedName("__v")
	val v: Int,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("user")
	val user: User
)

data class Product(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem2>,

	@field:SerializedName("step")
	val step: List<String>,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("categori")
	val categori: String,

	@field:SerializedName("gambar")
	val gambar: String
)
