package com.example.nutrichive.data.response

import com.google.gson.annotations.SerializedName

data class FavoriteResponse(

	@field:SerializedName("data")
	val data: DataFav,

	@field:SerializedName("message")
	val message: String
)

data class DataFav(

	@field:SerializedName("product")
	val product: String,

	@field:SerializedName("__v")
	val v: Int,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("user")
	val user: String
)
