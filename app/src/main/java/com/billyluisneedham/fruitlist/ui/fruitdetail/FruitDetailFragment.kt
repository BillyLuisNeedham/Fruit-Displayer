package com.billyluisneedham.fruitlist.ui.fruitdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.billyluisneedham.fruitlist.R
import com.billyluisneedham.fruitlist.databinding.FragmentDetailBinding
import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.ui.DiagnosticFragment
import com.billyluisneedham.fruitlist.utils.capitalise
import java.text.NumberFormat
import java.util.*

class FruitDetailFragment : DiagnosticFragment() {

    companion object {
        private const val WEIGHT_STRING = R.string.weight
        private const val KG = R.string.kg
        private const val PRICE_STRING = R.string.price
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
        binding.tvWeight.text = getWeightMessage()
        binding.tvPrice.text = getPriceMessage()
    }

    private fun getTypeFromArgs(): String {
        val type = requireArguments().getString(ARG_TYPE)
        return type?.capitalise() ?: throw IllegalStateException("$ARG_TYPE should not be null")
    }

    private fun getWeightMessage(): String {
        val weightInKg = getWeightInKgFromArgs()
        val prependWeightMessage = requireContext().getString(WEIGHT_STRING)
        val kg = requireContext().getString(KG)

        return "$prependWeightMessage $weightInKg$kg"
    }

    private fun getWeightInKgFromArgs(): String {
        val weight = requireArguments().getInt(ARG_WEIGHT)
        val convertedWeight = weight.toDouble() / 1000
        return convertedWeight.toString()
    }

    private fun getPriceMessage(): String {
        val priceMessage = requireContext().getString(PRICE_STRING)
        val priceInPence = requireArguments().getInt(ARG_PRICE)
        val priceInPounds = convertPenceToPounds(priceInPence.toDouble())
        return "$priceMessage $priceInPounds"
    }

    private fun convertPenceToPounds(priceInPence: Double): String {
        val n = NumberFormat.getCurrencyInstance(Locale.UK)
        return n.format(priceInPence / 100)
    }
}