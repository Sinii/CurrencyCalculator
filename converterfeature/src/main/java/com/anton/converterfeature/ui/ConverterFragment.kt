package com.anton.converterfeature.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anton.converterfeature.R
import com.anton.converterfeature.databinding.FragmentConverterBinding
import com.anton.converterfeature.ui.adapter.RateAdapter
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.example.base.di.ViewModelFactory
import com.example.base.ui.BaseFragment
import com.example.base.viewmodel.ViewModelCommands
import com.example.interfaces.ActivityDefaultBehavior
import com.example.interfaces.OnRateChangedListener
import com.example.utils.dLog


class ConverterFragment : BaseFragment<FragmentConverterBinding, ViewModelFactory>() {
    private var skeletonScreen: RecyclerViewSkeletonScreen? = null

    override fun provideListOfViewModels(): Array<Class<*>> = arrayOf(
        ConverterViewModel::class.java,
        TitleViewModel::class.java
    )

    override fun busEvents(
        command: ViewModelCommands,
        viewModelList: Set<*>,
        binding: FragmentConverterBinding
    ): Boolean {
        viewModelList.forEach { viewModel ->
            when {
                command is ViewModelCommands.DataLoaded
                        && viewModel is ConverterViewModel -> {
                    skeletonScreen?.hide()
                }
            }
        }
        return super.busEvents(command, viewModelList, binding)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun provideActionsBinding(): (FragmentConverterBinding, Set<*>) -> Unit = { binding, viewModelList ->

        viewModelList.forEach { viewModel ->
            when (viewModel) {
                is TitleViewModel -> {
                    viewModel.title.observe(this, Observer {
                        "title it=$it".dLog()
                        (activity as ActivityDefaultBehavior).setToolbarTitle(it)
                    })
                }
                is ConverterViewModel -> {
                    val layoutManager =
                        LinearLayoutManager(this@ConverterFragment.requireContext(), RecyclerView.VERTICAL, false)
                    val adapter = RateAdapter(ArrayList())
                    adapter.rateChangedListener =
                        object : OnRateChangedListener {
                            override fun onChanged(amount: Double, selectionStart: Int) {
                                viewModel.amountChanged(adapter.getItem(0), amount, selectionStart)
                            }
                        }
                    adapter.movedToTopLambda = { viewModel.itemMovedToTop(it) }
                    binding.ratesRecyclerView.layoutManager = layoutManager
                    binding.ratesRecyclerView.adapter = adapter
                    viewModel.ratesList.observe(this, Observer { list ->
                        if (list.isNotEmpty()) {
                            adapter.clearItems()
                            adapter.addItems(list)
                            adapter.notifyItemRangeChanged(1, list.size)
                        }
                    })
                    skeletonScreen = Skeleton
                        .bind(binding.ratesRecyclerView)
                        .adapter(adapter)
                        .load(R.layout.item_rate_skeleton)
                        .angle(0)
                        .color(R.color.shineLoadingColor)
                        .count(STUB_ITEMS_COUNT)
                        .show()
                }
            }
        }
    }

    override fun provideLayout() = R.layout.fragment_converter

    override fun provideLifecycleOwner() = this

    companion object {
        const val STUB_ITEMS_COUNT = 15
    }
}