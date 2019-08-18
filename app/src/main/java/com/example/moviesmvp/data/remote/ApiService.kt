package com.example.moviesmvp.data.remote

import com.example.moviesmvp.data.model.ListMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/upcoming/")
    fun getmMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("pages") page: Int
    ): Call<ListMovies>
}