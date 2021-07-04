package com.billyluisneedham.bbctest.ui.fruitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.billyluisneedham.bbctest.R
import com.billyluisneedham.bbctest.databinding.FragmentListBinding
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.utils.DependencyInjector
import com.billyluisneedham.bbctest.utils.Resource


class FruitListFragment(
    // used for test injection
    private val fruitRepository: FruitRepository? = null
) : Fragment() {

    companion object {
        private const val ERROR_MESSAGE = R.string.error_message
    }

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
        setOnClickOfRefreshButton()

        return binding.root
    }

    private fun setOnClickOfRefreshButton() {
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshFruits()
        }
    }

    private fun initRecyclerView() {
        adapter = FruitListAdapter()
        binding.rvList.adapter = adapter

        observeFruitListInViewModel()
    }

    private fun observeFruitListInViewModel() {
        viewModel.fruitList.observe(viewLifecycleOwner, {
            setLoadingUiIsVisible(false)
            setErrorUiVisibilityAndMessage(isVisible = false, message = null)
            when (it.status) {
                Resource.Status.LOADING -> setLoadingUiIsVisible(true)
                Resource.Status.SUCCESS -> adapter.submitList(it.data)
                Resource.Status.ERROR -> setErrorUiVisibilityAndMessage(
                    isVisible = true,
                    message = it.message
                )
            }
        })
    }

    private fun setErrorUiVisibilityAndMessage(isVisible: Boolean, message: String?) {
        val errorMessage = requireContext().getString(ERROR_MESSAGE)

        binding.tvErrorMsg.apply {
            this.isVisible = isVisible
            "$errorMessage $message".also { text = it }
        }

    }


    private fun setLoadingUiIsVisible(isVisible: Boolean) {
        binding.loadingUi.isVisible = isVisible
    }


}