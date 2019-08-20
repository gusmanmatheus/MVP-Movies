package com.example.moviesmvp.features.listMovies

import com.example.moviesmvp.data.local.MovieDAO
import com.example.moviesmvp.data.model.ListMovies
import com.example.moviesmvp.data.model.Movie
import com.example.moviesmvp.data.remote.ServiceRequest

class ListMoviesPresenter(
    override var view: ListMoviesContract.View,
    val service: ServiceRequest,
    val db: MovieDAO
) : ListMoviesContract.Presenter {

    override var sizePage: Int = 0
    override var listMovies = ListMovies(emptyList(), 1, 0)
    var current = -1
    override fun loadMore() {
        val stoptScroll = if (view.valueOrder()) {
            listMovies.pageCurrent >= listMovies.pagesTotal
        } else {
            listMovies.pageCurrent <= 1

        }
        if (stoptScroll) {
            view.onFinishLoad()
        } else {
            view.revertFInishLoad()
            nextPage()
        }
    }


    override fun nextPage() {
        val numberPage = if (view.valueOrder()) {
            listMovies.pageCurrent + 1
        } else {
            current +=1
            listMovies.pagesTotal - current
        }
        //  val numberPage =if (view.valueOrder()) 1 else -1
        service.getNextPage((numberPage),
            fun(success) {
                sizePage = success.list.size
                listMovies = success
                listMovies.list.forEach { it.moviePage = listMovies.pageCurrent }
                selectFavorites()
            },
            fun(failure) {
                var list = getAllRecord(-1)
                list.forEach {
                    it.favorite = true
                }
                view.showError(failure)
                view.setupList(list)

            })
    }

    private fun selectFavorites() {
        val list: List<Movie> = getAllRecord(listMovies.pageCurrent)

        listMovies.list.forEach {
            it.favorite = list.any { movie -> it == movie }
        }
        view.setupList(listMovies.list)
    }

    override fun inserOrDelete(movie: Movie): Boolean {
        var add = false
        if (verifyRecord(movie.id)) {
            deleteRecord(movie.id)
        } else {
            recordMovie(movie)
            add = true
        }
        return add
    }

    override fun verifyRecord(id: Int): Boolean {
        return db.getMovie(id).isNotEmpty()
    }

    override fun recordMovie(movie: Movie): Boolean {
        return db.insert(movie)

    }

    override fun deleteRecord(id: Int) {
        db.deleteMovie(id)
    }

    override fun getAllRecord(page: Int): List<Movie> {
        return db.getMovies(page)
    }

}