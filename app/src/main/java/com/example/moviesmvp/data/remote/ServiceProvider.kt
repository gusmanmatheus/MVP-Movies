package com.example.moviesmvp.data.remote


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceProvider() {
    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val request: ApiService = retrofit.create(ApiService::class.java)

    }

}