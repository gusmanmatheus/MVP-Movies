package com.example.moviesmvp.features.listMovies

import com.example.moviesmvp.base.BasePresenter
import com.example.moviesmvp.base.BaseView
import com.example.moviesmvp.data.model.ListMovies
import com.example.moviesmvp.data.model.Movie

interface ListMoviesContract {
    interface View : BaseView<Presenter> {
        fun setupList(list: List<Movie>)
        fun onFinishLoad()
    }

    interface Presenter : BasePresenter<View> {
        var listMovies: ListMovies
        var sizePage: Int
        fun nextPage()
    }
}