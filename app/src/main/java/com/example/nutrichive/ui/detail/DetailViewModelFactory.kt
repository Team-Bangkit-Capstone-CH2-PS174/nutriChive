package com.example.nutrichive.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nutrichive.data.di.Injection
import com.example.nutrichive.data.repository.RecipesRepository
import com.example.nutrichive.data.user.UserRepository

class DetailViewModelFactory (private val recipesRepository: RecipesRepository, private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(recipesRepository, userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: DetailViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: DetailViewModelFactory(Injection.provideRepository(), Injection.provideUserRepository(context))
            }.also { instance = it }
    }
}