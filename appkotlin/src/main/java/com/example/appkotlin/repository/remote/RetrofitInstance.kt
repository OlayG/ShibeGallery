package com.example.appkotlin.repository.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    // JAVAish Kotlin
    /*private fun getClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }*/

    private val client by lazy {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
                .let { OkHttpClient.Builder().addInterceptor(it).build() }
    }

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl("http://shibe.online/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
    }

    val shibeService = retrofit.create(ShibeService::class.java)
}