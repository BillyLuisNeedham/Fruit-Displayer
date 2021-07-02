package com.billyluisneedham.bbctest.ui.fruitlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.billyluisneedham.bbctest.databinding.ViewHolderFruitBinding
import com.billyluisneedham.bbctest.models.Fruit


class FruitListAdapter : ListAdapter<Fruit, FruitListAdapter.FruitViewHolder>(FruitDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        return FruitViewHolder(
            ViewHolderFruitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        val fruit = getItem(position)
        holder.bind(fruit)
    }

    class FruitViewHolder(private val binding: ViewHolderFruitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fruit: Fruit) {
            binding.fruit = fruit
        }
    }
}

object FruitDiffUtil : DiffUtil.ItemCallback<Fruit>() {

    override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
        return oldItem.fruitId == newItem.fruitId
    }

    override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
        return oldItem == newItem
    }
}
