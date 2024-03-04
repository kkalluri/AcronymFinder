package com.example.weatherapp.di

import com.example.acronymfinder.data.remote.AcronymAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {


    @Provides
    @Singleton
    fun provideOkHttpClient() =
        OkHttpClient.Builder().build()


    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): AcronymAPI =
        Retrofit.Builder()
            .baseUrl(AcronymAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AcronymAPI::class.java)

}


