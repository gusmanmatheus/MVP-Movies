package com.example.moviesmvp.data.remote

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceProvider(context: Context) {
    private var retrofit: Retrofit

    init {
        val client = OkHttpClient
            .Builder().addInterceptor(ChuckInterceptor(context))
            .build()

        this.retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val request: ApiService = retrofit.create(ApiService::class.java)
}