package com.batya.surftest.app.presentation.home.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batya.surftest.databinding.ItemCocktailBinding
import com.batya.surftest.domain.model.Cocktail


class CocktailAdapter(private val onClicked: (String) -> Unit): RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    var items: List<Cocktail> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding, onClicked)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CocktailViewHolder(
        private val binding: ItemCocktailBinding,
        private val onClicked: (String) -> Unit,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail: Cocktail) = with(binding) {
            tvCocktailTitle.text = cocktail.name

            //iv.setImageResource(R.drawable.ic_arrow_down)
            iv.setBackgroundColor(Color.GRAY)

            binding.cvCocktail.setOnClickListener {
                onClicked(cocktail.id)
                Log.d("TAG", "clicked ${cocktail.id}")
            }
        }
    }
}