package com.example.di.module

import com.example.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        ConverterFeatureViewModelModule::class//,
        //ViewModelFactory::class
    ]
)
abstract class MainActivityModule {

    @ContributesAndroidInjector(
        modules = [ConverterFeatureFragmentBuildersModule::class]
    )

    abstract fun contributeMainActivity(): MainActivity

}