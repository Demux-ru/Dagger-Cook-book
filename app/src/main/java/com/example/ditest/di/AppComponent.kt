package com.example.ditest.di

import android.app.Application
import android.content.Context
import com.example.ditest.DiTestApp
import com.example.ditest.di.module.AppModule
import com.example.ditest.di.module.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector

@Component(modules = [
	AppModule::class,
	DataModule::class
])
@AppScope
interface AppComponent : AndroidInjector<DiTestApp> {

	@Component.Builder
	abstract class Builder : AndroidInjector.Builder<DiTestApp>() {

		@BindsInstance
		abstract fun app(app: Application): Builder

		@BindsInstance
		abstract fun context(context: Context): Builder
	}
}