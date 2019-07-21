package com.anton.converterfeature.ui

import androidx.lifecycle.MutableLiveData
import com.example.base.viewmodel.BaseViewModel
import javax.inject.Inject

class TitleViewModel
@Inject constructor() : BaseViewModel() {
    val title = MutableLiveData<String>()

    override fun doAutoMainWork() {
        title.postValue("Conversions")
    }
}