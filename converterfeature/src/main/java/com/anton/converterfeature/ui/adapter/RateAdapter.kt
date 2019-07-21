package com.anton.converterfeature.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.anton.converterfeature.R
import com.anton.converterfeature.databinding.ItemRateBinding
import com.example.base.adapters.BaseAdapter
import com.example.interfaces.OnRateChangedListener
import com.example.rates.ExchangeRateItem
import com.example.utils.dLog

class RateAdapter(rateList: ArrayList<ExchangeRateItem>) :
    BaseAdapter<RateViewHolder, ExchangeRateItem>(rateList) {

    lateinit var rateChangedListener: OnRateChangedListener
    lateinit var movedToTopLambda: (ExchangeRateItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {

        val binding = DataBindingUtil.inflate<ItemRateBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rate,
            parent,
            false
        )
        val viewHolder = RateViewHolder(binding)
        val moveItemToTopLambda: (Int) -> Unit = { position ->
            "moveItemToTopLambda count = $itemCount".dLog()
            if (position > 0) {
                movedToTopLambda.invoke(getItem(position))
                moveItemToTop(position)
            }
        }
        viewHolder.setListeners(rateChangedListener, moveItemToTopLambda)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}