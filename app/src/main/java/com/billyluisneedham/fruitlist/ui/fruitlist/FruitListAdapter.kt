package com.billyluisneedham.fruitlist.ui.fruitlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.billyluisneedham.fruitlist.databinding.ViewHolderFruitBinding
import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.utils.capitalise


class FruitListAdapter(private val callbacks: IFruitListViewHolderCallbacks) :
    ListAdapter<Fruit, FruitListAdapter.FruitViewHolder>(FruitDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        return FruitViewHolder(
            ViewHolderFruitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        val fruit = getItem(position)
        holder.bind(fruit)
    }

    inner class FruitViewHolder(private val binding: ViewHolderFruitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fruit: Fruit) {
            binding.tvFruit.text = fruit.type.capitalise()
            binding.cardViewHolder.setOnClickListener {
                callbacks.onClickFruitViewHolder(fruit)
            }
        }
    }


    interface IFruitListViewHolderCallbacks {
        fun onClickFruitViewHolder(fruit: Fruit)
    }

    object FruitDiffUtil : DiffUtil.ItemCallback<Fruit>() {

        override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
            return oldItem.fruitId == newItem.fruitId
        }

        override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
            return oldItem == newItem
        }
    }
}