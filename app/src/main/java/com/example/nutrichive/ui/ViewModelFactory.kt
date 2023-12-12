package com.example.nutrichive.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nutrichive.data.di.Injection
import com.example.nutrichive.data.repository.RecipesRepository
import com.example.nutrichive.data.user.UserRepository
import com.example.nutrichive.ui.detail.DetailViewModel
import com.example.nutrichive.ui.home.HomeViewModel
import com.example.nutrichive.ui.login.LoginViewModel
import com.example.nutrichive.ui.search.SearchViewModel

class ViewModelFactory(private val recipesRepository: RecipesRepository, private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(recipesRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(recipesRepository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(recipesRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(userRepository: UserRepository) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(), userRepository)
            }.also { instance = it }
    }
}