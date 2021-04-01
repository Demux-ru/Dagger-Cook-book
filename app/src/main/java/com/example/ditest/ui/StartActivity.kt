package com.example.ditest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ditest.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class StartActivity : AppCompatActivity(), HasAndroidInjector {

	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Any>

	override fun onCreate(savedInstanceState: Bundle?) {
		AndroidInjection.inject(this)
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_start)
	}

	override fun androidInjector(): AndroidInjector<Any> = androidInjector
}