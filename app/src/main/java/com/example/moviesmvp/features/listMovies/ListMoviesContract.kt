package com.example.moviesmvp.features.listMovies

import com.example.moviesmvp.base.BasePresenter
import com.example.moviesmvp.base.BaseView

interface ListMoviesContract {
    interface View:BaseView<Presenter>{

    }
    interface Presenter:BasePresenter<View>
}