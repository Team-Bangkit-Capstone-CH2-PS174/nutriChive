package com.example.nutrichive.ui.saved

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nutrichive.data.response.DataItem2
import com.example.nutrichive.databinding.ItemRecipeBinding
import com.example.nutrichive.ui.detail.DetailActivity

class FavRecipeAdapter: ListAdapter<DataItem2, FavRecipeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavRecipeAdapter.MyViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavRecipeAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = getItem(position)
        if (recipe != null) {
            holder.bind(recipe)
        }
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("detail", recipe.product.id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class MyViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: DataItem2){
            binding.recipeTitle.text = recipe.product.name
            Glide.with(itemView.context)
                .load(recipe.product.gambar)
                .into(binding.recipeImage)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem2>() {
            override fun areItemsTheSame(
                oldItem: DataItem2,
                newItem: DataItem2
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem2,
                newItem: DataItem2
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}