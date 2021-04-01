package com.example.ditest

import android.app.Application
import androidx.core.app.AppComponentFactory
import com.example.ditest.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DiTestApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .app(this)
            .context(this)
            .create(this)
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}