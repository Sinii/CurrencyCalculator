package com.anton.converterfeature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anton.converterfeature.ui.ConverterViewModel
import com.anton.converterfeature.ui.TitleViewModel
import com.example.base.di.ViewModelFactory
import com.example.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ConverterFeatureViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TitleViewModel::class)
    internal abstract fun bindTitleViewModel(viewModel: TitleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConverterViewModel::class)
    internal abstract fun bindConverterViewModel(viewModel: ConverterViewModel): ViewModel

    //Add more ViewModels here
}