package com.example.di.module

import com.anton.converterfeature.di.ConverterFeatureFragmentBuildersModule
import com.anton.converterfeature.di.ConverterFeatureViewModelModule
import com.example.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        ConverterFeatureViewModelModule::class
    ]
)
abstract class MainActivityModule {

    @ContributesAndroidInjector(
        modules = [ConverterFeatureFragmentBuildersModule::class]
    )

    abstract fun contributeMainActivity(): MainActivity

}