package com.example.ditest.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.example.ditest.data.model.CurrencyModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

interface Preferences {

	fun setCurrenciesList(key: String, list: List<CurrencyModel>)
	fun getCurrenciesList(key: String): List<CurrencyModel>
}

class PreferencesImpl @Inject constructor(
	private val gson: Gson,
	context: Context
) : Preferences {

	private companion object {
		const val CURRENCIES_PREFS = "CURRENCIES_PREFS"
	}

	private val sharedPreferences: SharedPreferences =
		context.getSharedPreferences(CURRENCIES_PREFS, Context.MODE_PRIVATE)

	override fun setCurrenciesList(key: String, list: List<CurrencyModel>) {
		val json = gson.toJson(list)
		val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
		prefsEditor.putString(key, json)
		prefsEditor.apply()
	}

	override fun getCurrenciesList(key: String): List<CurrencyModel> {
		val string = sharedPreferences.getString(key, null)
		val type = object : TypeToken<List<CurrencyModel>>() {}.type
		return gson.fromJson(string, type)
	}
}