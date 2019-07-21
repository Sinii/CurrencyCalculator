package com.example.di.module


import com.anton.converterfeature.ui.ConverterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ConverterFeatureFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeConverterFragment(): ConverterFragment

    //Add more Fragments here

}
