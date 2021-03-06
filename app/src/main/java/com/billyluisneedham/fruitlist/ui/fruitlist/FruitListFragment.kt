package com.billyluisneedham.fruitlist.ui.fruitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.billyluisneedham.fruitlist.R
import com.billyluisneedham.fruitlist.databinding.FragmentListBinding
import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.ui.DiagnosticFragment
import com.billyluisneedham.fruitlist.ui.MainActivity
import com.billyluisneedham.fruitlist.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FruitListFragment: DiagnosticFragment(), FruitListAdapter.IFruitListViewHolderCallbacks {

    companion object {
        private const val ERROR_MESSAGE = R.string.error_message
    }

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: FruitListAdapter
    private val viewModel: FruitListViewModel by viewModels()

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
        adapter = FruitListAdapter(this)
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

    override fun onClickFruitViewHolder(fruit: Fruit) {
        (requireActivity() as MainActivity).onRequestChangeOfUi(System.currentTimeMillis())

        findNavController().navigate(
            FruitListFragmentDirections.actionFruitListFragmentToFruitDetailFragment(
                fruit.type,
                fruit.price,
                fruit.weight
            )
        )
    }


}