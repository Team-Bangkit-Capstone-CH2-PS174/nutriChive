package com.example.nutrichive.data.response

import com.google.gson.annotations.SerializedName

data class AllRecipesResponse(

	@field:SerializedName("data")
	val data: List<DataItem>
)

data class IngredientsItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("name")
	val name: String
)

data class DataItem(

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem>,

	@field:SerializedName("step")
	val step: List<String>,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("categori")
	val categori: String
)
