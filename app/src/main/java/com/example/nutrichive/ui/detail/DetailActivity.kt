package com.example.nutrichive.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nutrichive.R
import com.example.nutrichive.data.response.DataItem
import com.example.nutrichive.data.response.DetailRecipesResponse
import com.example.nutrichive.databinding.ActivityDetailBinding
import com.example.nutrichive.ui.ViewModelFactory
import com.example.nutrichive.utils.ResultState

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance()
    }
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