package com.example.nutrichive.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nutrichive.data.response.IngredientsItemD
import com.example.nutrichive.databinding.ItemBahanListBinding

class BahanAdapter(private val ingredients: List<IngredientsItemD>) : RecyclerView.Adapter<BahanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBahanListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    override fun getItemCount() = ingredients.size

    inner class ViewHolder(val binding: ItemBahanListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: IngredientsItemD) {
            binding.titleBahanDetail.text = ingredient.name
            Glide.with(itemView.context)
                .load(ingredient.image)
                .into(binding.imageBahanDetail)
        }
    }
}