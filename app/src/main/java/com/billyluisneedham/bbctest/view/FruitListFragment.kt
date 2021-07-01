package com.billyluisneedham.bbctest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.billyluisneedham.bbctest.databinding.FragmentListBinding
import com.billyluisneedham.bbctest.viewmodel.FruitListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FruitListFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(FruitListViewModel::class.java)
    }
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            binding = this
        }

        binding.tvTEST.text = viewModel.someString

        return binding.root
    }


}