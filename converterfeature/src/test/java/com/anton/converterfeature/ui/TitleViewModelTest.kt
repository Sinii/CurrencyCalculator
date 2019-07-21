package com.anton.converterfeature.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class TitleViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getTitle() {
        val vm = TitleViewModel()
        vm.doAutoMainWork()
        print(vm.title.value)

        assertTrue(vm.title.value == "Conversions")
    }
}