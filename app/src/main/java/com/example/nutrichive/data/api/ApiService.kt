package com.example.nutrichive.data.api

import com.example.nutrichive.data.response.AllRecipesResponse
import com.example.nutrichive.data.response.DataItem
import com.example.nutrichive.data.response.DetailRecipesResponse
import com.example.nutrichive.data.response.FavoriteResponse
import com.example.nutrichive.data.response.ListFavoriteResponse
import com.example.nutrichive.data.response.LoginResponse
import com.example.nutrichive.data.response.RegisterResponse
import com.example.nutrichive.data.response.SearchFavResponse
import com.example.nutrichive.data.response.UnfavoriteResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("auth/signup")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String,
        @Field("phoneNumber") phoneNumber: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/signin")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
    @GET("products")
    fun getAllRecipes(): Call<AllRecipesResponse>

    @GET("products")
    fun getRandomRecipes(): Call<AllRecipesResponse>

    @GET("products/detail/{id}")
    fun getDetailRecipes(
        @Path("id") id: String
    ): Call<DetailRecipesResponse>

    @GET("products/search")
    fun searchRecipes(
        @Query("keyword") keyword: String
    ): Call<AllRecipesResponse>

    @GET("products/searchFav")
    fun searchFav(
        @Header("Authorization") token: String,
        @Query("id_product") id_product: String
    ): Call<SearchFavResponse>

    @FormUrlEncoded
    @POST("products/favorite")
    fun addFavorite(
        @Header("Authorization") token: String,
        @Field("product") product: String,
    ): Call<FavoriteResponse>

    @FormUrlEncoded
    @POST("products/unfavorite")
    fun dropFavorite(
        @Header("Authorization") token: String,
        @Field("product") product: String,
    ): Call<UnfavoriteResponse>

    @GET("products/listfavorite")
    fun getListFavorite(
        @Header("Authorization") token: String,
    ): Call<ListFavoriteResponse>
}