package com.billyluisneedham.fruitlist.ui.fruitdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.billyluisneedham.fruitlist.R
import com.billyluisneedham.fruitlist.databinding.FragmentDetailBinding
import com.billyluisneedham.fruitlist.ui.DiagnosticFragment
import com.billyluisneedham.fruitlist.utils.capitalise
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class FruitDetailFragment : DiagnosticFragment() {

    companion object {
        private const val WEIGHT_STRING = R.string.weight
        private const val KG = R.string.kg
        private const val PRICE_STRING = R.string.price
    }

    private val args: FruitDetailFragmentArgs by navArgs()
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
        binding.tvFruitType.text = args.type.capitalise()
        binding.tvWeight.text = getWeightMessage()
        binding.tvPrice.text = getPriceMessage()
    }

    private fun getWeightMessage(): String {
        val weightInKg = getWeightInKgFromArgs()
        val prependWeightMessage = requireContext().getString(WEIGHT_STRING)
        val kg = requireContext().getString(KG)

        return "$prependWeightMessage $weightInKg$kg"
    }

    private fun getWeightInKgFromArgs(): String {
        val weight = args.weight
        val convertedWeight = weight.toDouble() / 1000
        return convertedWeight.toString()
    }

    private fun getPriceMessage(): String {
        val priceMessage = requireContext().getString(PRICE_STRING)
        val priceInPence = args.price
        val priceInPounds = convertPenceToPounds(priceInPence.toDouble())
        return "$priceMessage $priceInPounds"
    }

    private fun convertPenceToPounds(priceInPence: Double): String {
        val n = NumberFormat.getCurrencyInstance(Locale.UK)
        return n.format(priceInPence / 100)
    }
}