package com.example.ditest.di.module

import com.example.ditest.data.api.ApiConstant.BASE_URL
import com.example.ditest.data.api.CurrencyApiClient
import com.example.ditest.di.AppScope
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    @AppScope
    fun provideCurrencyApiClient(retrofit: Retrofit): CurrencyApiClient =
        retrofit.create(CurrencyApiClient::class.java)

    @Provides
    @AppScope
    fun provideRetrofit(
        okHttpClient: OkHttpClient?,
        gsonConverterFactory: GsonConverterFactory?
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @AppScope
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @AppScope
    fun provideGson(): Gson = Gson()
}