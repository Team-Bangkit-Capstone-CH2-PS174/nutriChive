package com.example.nutrichive.data.response

import com.google.gson.annotations.SerializedName

data class SearchFavResponse(

	@field:SerializedName("data")
	val data: List<DataItemFav>
)

data class DataItemFav(

	@field:SerializedName("product")
	val product: String,

	@field:SerializedName("__v")
	val v: Int,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("user")
	val user: String
)
