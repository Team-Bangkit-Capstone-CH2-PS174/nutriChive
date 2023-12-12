package com.example.nutrichive.data.api

import com.example.nutrichive.data.response.AllRecipesResponse
import com.example.nutrichive.data.response.DetailRecipesResponse
import com.example.nutrichive.data.response.LoginResponse
import com.example.nutrichive.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
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