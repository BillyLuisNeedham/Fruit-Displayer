package com.billyluisneedham.fruitlist.ui.fruitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.billyluisneedham.fruitlist.R
import com.billyluisneedham.fruitlist.databinding.FragmentListBinding
import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.source.FruitRepository
import com.billyluisneedham.fruitlist.ui.DiagnosticFragment
import com.billyluisneedham.fruitlist.ui.MainActivity
import com.billyluisneedham.fruitlist.ui.fruitdetail.FruitDetailFragment
import com.billyluisneedham.fruitlist.utils.DependencyInjector
import com.billyluisneedham.fruitlist.utils.Resource


class FruitListFragment(
    // used for test injection
    private val fruitRepository: FruitRepository? = null
) : DiagnosticFragment(), FruitListAdapter.IFruitListViewHolderCallbacks {

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

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.flMainActivity, FruitDetailFragment.newInstance(fruit))
            .addToBackStack(null)
            .commit()
    }


}