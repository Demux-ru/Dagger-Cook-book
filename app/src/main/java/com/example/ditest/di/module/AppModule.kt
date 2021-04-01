package com.example.ditest.di.module

import com.example.ditest.di.ActivityScope
import com.example.ditest.ui.StartActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [StartActivityModule::class])
    fun injectStartActivity(): StartActivity
}