package com.example.nutrichive.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.nutrichive.data.response.DataItem2
import com.example.nutrichive.data.user.UserModel
import com.example.nutrichive.data.user.UserRepository
import com.example.nutrichive.utils.ResultState

class SaveViewModel(private val repository: UserRepository): ViewModel() {
    fun getListFavorite(token: String?): LiveData<ResultState<List<DataItem2>?>> = repository.getListFavorite(token)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}