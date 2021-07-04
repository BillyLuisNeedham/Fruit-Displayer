package com.billyluisneedham.bbctest.ui.fruitdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.billyluisneedham.bbctest.databinding.FragmentDetailBinding
import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.utils.capitalise

class FruitDetailFragment : Fragment() {

    companion object {
        const val ARG_TYPE = "arg_type"
        const val ARG_PRICE = "arg_price"
        const val ARG_WEIGHT = "arg_weight"

        fun newInstance(fruit: Fruit) = FruitDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TYPE, fruit.type)
                putInt(ARG_PRICE, fruit.price)
                putInt(ARG_WEIGHT, fruit.weight)
            }
        }
    }

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            binding = this
        }

        setUiDetailsFromArguments()

        return binding.root
    }

    private fun setUiDetailsFromArguments() {
        binding.tvFruitType.text = getTypeFromArgs()

    }

    private fun getTypeFromArgs(): String {
        val type = requireArguments().getString(ARG_TYPE)
        return type?.capitalise() ?: throw IllegalStateException("$ARG_TYPE should not be null")
    }
}