package com.example.moviesmvp.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val title: String,
    @SerializedName("poster_path")
    val image: String,
    @SerializedName("poster_path")
    val date: String,
    @SerializedName("overview")
    val synopsis: String
)

data class ListMovies(
    @SerializedName("results")
    val list: List<Movie>,
    @SerializedName("total_pages")
    val pagesTotal: Int,
    @SerializedName("page")
    val pageCurrent: Int
)

