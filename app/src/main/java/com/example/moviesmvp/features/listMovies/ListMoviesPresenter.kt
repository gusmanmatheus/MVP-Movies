package com.example.moviesmvp.features.listMovies

import android.util.Log
import com.example.moviesmvp.data.model.ListMovies
import com.example.moviesmvp.data.remote.ServiceRequest

class ListMoviesPresenter(
    override var view: ListMoviesContract.View,
    val service: ServiceRequest
) : ListMoviesContract.Presenter {
   override var sizePage:Int = 0
    override var listMovies = ListMovies(emptyList(), 1, 0)


    fun loadMore() {
        if (listMovies.pageCurrent >= listMovies.pagesTotal) {
            view.onFinishLoad()
        } else {
            nextPage()
        }

    }

    override fun nextPage() {
        service.getNextPage((this.listMovies.pageCurrent+1),
            fun(success){
                sizePage = success.list.size
                listMovies = success
                view.setupList(listMovies.list)
            },
            fun(failure){

            })

    }

}