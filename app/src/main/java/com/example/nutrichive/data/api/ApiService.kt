package com.example.nutrichive.data.api

import com.example.nutrichive.data.response.AllRecipesResponse
import com.example.nutrichive.data.response.DetailRecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    fun getAllRecipes(): Call<AllRecipesResponse>

    @GET("products/detail/{id}")
    fun getDetailRecipes(
        @Path("id") id: String
    ): Call<DetailRecipesResponse>

    @GET("products/search")
    fun searchRecipes(
        @Query("keyword") keyword: String
    ): Call<AllRecipesResponse>
}