package com.example.acronymfinder.di

import com.example.acronymfinder.data.remote.AcronymAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


    val instance: AcronymAPI by lazy {

        var loggerInterCeptor = HttpLoggingInterceptor()
        loggerInterCeptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        var okHttpClient = OkHttpClient.Builder().addInterceptor(loggerInterCeptor).build()


        val retrofit = Retrofit.Builder()
            .baseUrl(AcronymAPI.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AcronymAPI::class.java)

    }


}
