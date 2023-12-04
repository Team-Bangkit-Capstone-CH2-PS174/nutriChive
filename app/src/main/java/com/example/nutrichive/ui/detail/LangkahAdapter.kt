package com.example.nutrichive.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichive.databinding.ItemLangkahListBinding

class LangkahAdapter(private val step: List<String>) : RecyclerView.Adapter<LangkahAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLangkahListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = step[position]
        holder.bind(ingredient)
    }

    override fun getItemCount() = step.size

    inner class ViewHolder(val binding: ItemLangkahListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(step: String?) {
            binding.langkahDetail.text = step
        }
    }
}