package com.anton.converterfeature.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import com.example.base.viewmodel.BaseViewModel
import com.example.base.viewmodel.ViewModelCommands
import com.example.rates.ExchangeRateItem
import com.example.usecase.exchangerates.GetCalculatedRateItemsUseCase
import com.example.utils.dLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

class ConverterViewModel
@Inject constructor(
    private val getCalculatedRateItemsUseCase: GetCalculatedRateItemsUseCase
) : BaseViewModel() {

    val ratesList = MutableLiveData<List<ExchangeRateItem>>()
    val showNoItemsStub = MutableLiveData(GONE)
    private var selectedCurrency = BASE_CURRENCY
    private var infinityRequestJob: Job? = null
    private var debounceJob: Job? = null

    override fun restoreViewModel() {
        if (ratesList.value?.isNotEmpty() == true) {
            viewModelCommands.postValue(ViewModelCommands.DataLoaded)
        } else {
            doAutoMainWork()
        }
    }

    override fun doAutoMainWork() {
        viewModelCommands.postValue(ViewModelCommands.DataStartLoading)
        "doAutoMainWork".dLog()
        reInitInfinityJob()
    }

    private fun updateRatesJob() = doInfiniteRepeatWork {
        "updateRatesJob after delay selectedCurrency = $selectedCurrency".dLog()
        val exchangeRatesResult = getCalculatedRateItemsUseCase
            .doWork(GetCalculatedRateItemsUseCase.Params(selectedCurrency))
        val exchangeRatesItems = exchangeRatesResult.exchangeRatesItems
        val hasItems = !exchangeRatesItems.isNullOrEmpty()
        if (hasItems) {
            showNoItemsStub.postValue(GONE)
            ratesList.postValue(exchangeRatesItems)
        } else {
            showNoItemsStub.postValue(VISIBLE)
            showError(exchangeRatesResult.errorMessage)
        }
        viewModelCommands.postValue(ViewModelCommands.DataLoaded)
        delay(RATE_UPDATE_DELAY)
    }

    fun amountChanged(item: ExchangeRateItem, amountValue: Double, selectionStart: Int) {
        "item = ${item.title} old amount = ${item.amount} amount = $amountValue".dLog()
        debounceJob?.cancel()
        debounceJob = doWork {
            selectedCurrency = ExchangeRateItem(
                item.code,
                item.title,
                amountValue,
                item.currencyUrl,
                selectionStart
            )
            reInitInfinityJob()
            delay(RATE_DELAY_DEBOUNCE)
        }
    }

    fun itemMovedToTop(item: ExchangeRateItem) {
        selectedCurrency = item
        reInitInfinityJob()
    }

    private fun reInitInfinityJob() {
        infinityRequestJob?.cancel()
        infinityRequestJob = updateRatesJob()
    }

    companion object {
        val BASE_CURRENCY = ExchangeRateItem("EUR", "EUR", 1.0, null)
        const val RATE_DELAY_DEBOUNCE = 300L
        const val RATE_UPDATE_DELAY = 3000L
    }
}