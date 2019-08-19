package com.example.moviesmvp.base.modules

import com.example.moviesmvp.data.local.MovieDAO
import com.example.moviesmvp.data.remote.ServiceProvider
import com.example.moviesmvp.data.remote.ServiceRequest
import com.example.moviesmvp.features.listMovies.ListMoviesContract
import com.example.moviesmvp.features.listMovies.ListMoviesPresenter
import org.koin.dsl.bind
import org.koin.dsl.module

val moduleBase = module {
    factory { ServiceProvider(get()) }
    factory { ServiceRequest(get()) }
    factory { MovieDAO(get()) }
}

val moduleListModules = module {
    factory { (view: ListMoviesContract.View) ->
        ListMoviesPresenter(
            view = view,
            service = get(),
            db = get()
        )
    } bind ListMoviesPresenter::class
}