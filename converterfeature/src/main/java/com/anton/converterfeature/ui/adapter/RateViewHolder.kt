package com.anton.converterfeature.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anton.converterfeature.databinding.ItemRateBinding
import com.example.interfaces.OnRateChangedListener
import com.example.interfaces.OnTextChangedSimpleListener
import com.example.rates.ExchangeRateItem
import com.example.utils.loadImage
import kotlinx.android.synthetic.main.item_rate.view.*
import java.text.DecimalFormat

class RateViewHolder(binding: ItemRateBinding) : RecyclerView.ViewHolder(binding.root) {
    private var textWatcher: TextWatcher? = null

    fun setListeners(onRateChangedListener: OnRateChangedListener, moveItemToTopLambda: (Int) -> Unit) {
        with(itemView) {
            textWatcher = object : OnTextChangedSimpleListener {
                override fun afterTextChanged(text: Editable?) {
                    try {
                        val amountText = text.toString()
                        val amount = amountText.toDouble()
                        if (amount >= 0) {
                            onRateChangedListener.onChanged(amount, rateAmountEditText.selectionStart)
                        }
                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }
                }
            }
            rateAmountEditText.onFocusChangeListener = View.OnFocusChangeListener { _, focused ->
                if (focused) {
                    moveItemToTopLambda.invoke(adapterPosition)
                    rateAmountEditText.addTextChangedListener(textWatcher)
                } else {
                    rateAmountEditText.removeTextChangedListener(textWatcher)
                }
            }
        }
    }

    fun bind(item: ExchangeRateItem?) {
        with(itemView) {
            if (item != null) {
                currencyImageView.loadImage(item.currencyUrl)
                rateTextView.text = item.code
                rateNameTextView.text = item.title
                val amount = DecimalFormat("#.###").format(item.amount)
                rateAmountEditText.setText(amount)
                val selection = item.selection
                val textLength = rateAmountEditText.text?.length
                if (selection != null && textLength != null) {
                    rateAmountEditText.setSelection(
                        if (textLength >= selection) selection else textLength
                    )
                }
            }
        }
    }
}