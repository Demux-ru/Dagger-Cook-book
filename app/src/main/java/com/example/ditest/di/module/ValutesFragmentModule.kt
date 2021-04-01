package com.example.ditest.di.module

import androidx.lifecycle.ViewModel
import com.example.ditest.di.ViewModelKey
import com.example.ditest.presentation.ValutesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ValutesFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(ValutesViewModel::class)
    fun bindValuteViewModel(viewModel: ValutesViewModel): ViewModel
}