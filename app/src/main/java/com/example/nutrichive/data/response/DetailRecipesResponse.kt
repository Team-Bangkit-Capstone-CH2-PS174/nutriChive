package com.example.nutrichive.data.response

import com.google.gson.annotations.SerializedName

data class DetailRecipesResponse(

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItemD>,

	@field:SerializedName("step")
	val step: List<String>,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("categori")
	val categori: String
)

data class IngredientsItemD(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("name")
	val name: String
)
