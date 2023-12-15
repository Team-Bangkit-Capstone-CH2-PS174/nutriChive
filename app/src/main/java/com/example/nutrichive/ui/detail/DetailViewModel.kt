package com.example.nutrichive.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.nutrichive.data.repository.RecipesRepository
import com.example.nutrichive.data.response.DataItemFav
import com.example.nutrichive.data.response.DetailRecipesResponse
import com.example.nutrichive.data.user.UserModel
import com.example.nutrichive.data.user.UserRepository
import com.example.nutrichive.utils.ResultState

class DetailViewModel(private val recipesRepository: RecipesRepository, private val userRepository: UserRepository): ViewModel() {

    fun getDetailRecipes(id: String): LiveData<ResultState<DetailRecipesResponse?>> = recipesRepository.getDetailRecipes(id)

    fun addFavorite(token: String?, idRecipe: String) =
        userRepository.addFavorite(token, idRecipe)

    fun dropFavorite(token: String?, idRecipe: String) =
        userRepository.dropFavorite(token, idRecipe)

    fun searchFav(token: String?, idRecipe: String) : LiveData<ResultState<List<DataItemFav>?>> =
        userRepository.searchFav(token, idRecipe)

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}