package com.billyluisneedham.bbctest.ui.fruitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.billyluisneedham.bbctest.databinding.FragmentListBinding
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.utils.DependencyInjector


class FruitListFragment(
    // used for test injection
    private val fruitRepository: FruitRepository? = null
) : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: FruitListAdapter
    private val viewModel: FruitListViewModel by viewModels {
        FruitListViewModel.Factory(
            fruitRepository
                ?: DependencyInjector.provideFruitRepository(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            binding = this
        }

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = FruitListAdapter()
        binding.rvList.adapter = adapter

        observeFruitListInViewModel()
    }

    private fun observeFruitListInViewModel() {
        viewModel.fruitList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }


}