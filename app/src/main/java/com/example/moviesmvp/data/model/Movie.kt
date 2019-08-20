package com.example.moviesmvp.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("release_date")
    val date: String,
    @SerializedName("poster_path")
    var image: String = "",
    @SerializedName("overview")
    val synopsis: String,
    var moviePage: Int,
    var favorite: Boolean = false
)

class ListMovies(
    @SerializedName("results")
    val list: List<Movie>,
    @SerializedName("total_pages")
    var pagesTotal: Int,
    @SerializedName("page")
    var pageCurrent: Int
)

