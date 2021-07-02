package com.billyluisneedham.bbctest.ui.fruitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.billyluisneedham.bbctest.databinding.FragmentListBinding
import com.billyluisneedham.bbctest.utils.DependencyInjector
import kotlinx.coroutines.launch


class FruitListFragment: Fragment() {

    private lateinit var binding: FragmentListBinding

    //TODO restore
//    private val viewModel: FruitListViewModel by viewModels {
//        FruitListViewModel.Factory(
//            DependencyInjector.getFruitRepository()
//        )
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            binding = this
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val repo = DependencyInjector.provideFruitRepository(requireContext())
            val list = repo.getFruits()
            val text = list.toString()
        binding.tvTEST.text = text
        }

        return binding.root
    }


}