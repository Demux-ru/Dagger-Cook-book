package com.example.ditest.di.module

import android.app.Activity
import com.example.ditest.di.ActivityScope
import com.example.ditest.di.FragmentScope
import com.example.ditest.ui.StartActivity
import com.example.ditest.ui.ValutesFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface StartActivityModule {

    @Binds
    @ActivityScope
    fun bindStartActivity(activity: StartActivity): Activity

    @FragmentScope
    @ContributesAndroidInjector(modules = [ValutesFragmentModule::class])
    fun provideValutesFragment(): ValutesFragment
}