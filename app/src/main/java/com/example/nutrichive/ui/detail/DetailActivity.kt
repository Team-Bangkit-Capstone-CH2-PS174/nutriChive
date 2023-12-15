package com.example.nutrichive.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nutrichive.R
import com.example.nutrichive.data.response.DataItemFav
import com.example.nutrichive.data.response.DetailRecipesResponse
import com.example.nutrichive.databinding.ActivityDetailBinding
import com.example.nutrichive.ui.login.LoginActivity
import com.example.nutrichive.ui.main.MainActivity
import com.example.nutrichive.utils.ResultState

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory.getInstance(this)
    }
    private var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val id = intent.getStringExtra("detail")

        viewModel.getDetailRecipes("$id").observe(this) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    val recipeResponse = resultState.data
                    if (recipeResponse != null) {
                        setDetailRecipes(recipeResponse)
                    }
                }

                is ResultState.Error -> {
                }

                is ResultState.Loading -> {
                }
            }
        }
        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                viewModel.searchFav(user.token, "$id").observe(this@DetailActivity) { data ->
                    when(data) {
                        is ResultState.Success -> {
                            val favorite = data.data
                            if (favorite != null) {
                                updateSave(favorite)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
        binding.saveButton.setOnClickListener {
            viewModel.getSession().observe(this) {
                if (it.isLogin) {
                    if (isFavorite) {
                        viewModel.dropFavorite(it.token, "$id").observe(this) {result ->
                            if(result != null) {
                                when (result) {
                                    is ResultState.Success -> {
                                        recreate()
                                    }
                                    else -> {}
                                }
                            }
                        }
                    } else {
                        viewModel.addFavorite(it.token, "$id").observe(this) {result ->
                            if(result != null) {
                                when (result) {
                                    is ResultState.Success -> {
                                        recreate()
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateSave(favorite: List<DataItemFav>) {
        val fav = favorite[0].product
        isFavorite = fav.isNotEmpty()
        if (isFavorite) {
            binding.saveButton.setImageResource(R.drawable.bookmark_svgrepo_favorit)
        } else {
            binding.saveButton.setImageResource(R.drawable.bookmark_svgrepo_com)
        }
    }

    private fun setDetailRecipes(item: DetailRecipesResponse) {
        binding.apply {
            titleDetail.text = item.name
            Glide.with(this@DetailActivity)
                .load(item.gambar)
                .into(binding.imageDetail)
            descDetail.text = item.description
            descDetail.text = item.description

            rvBahan.apply {
                adapter = BahanAdapter(item.ingredients)
                layoutManager = LinearLayoutManager(this@DetailActivity)
            }

            rvLangkah.apply {
                adapter = LangkahAdapter(item.step)
                layoutManager = LinearLayoutManager(this@DetailActivity)
            }
        }
    }
}