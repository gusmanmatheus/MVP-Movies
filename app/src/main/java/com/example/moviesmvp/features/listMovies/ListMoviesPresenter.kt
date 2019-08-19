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

    override fun loadMore() {
        if (listMovies.pageCurrent >= listMovies.pagesTotal) {
            view.onFinishLoad()
        } else {
            nextPage()
        }
    }

    override fun nextPage() {
        service.getNextPage((this.listMovies.pageCurrent + 1),
            fun(success) {
                sizePage = success.list.size
                listMovies = success
                listMovies.list.forEach { it.moviePage = listMovies.pageCurrent }
                selectFavorites()
            },
            fun(failure) {
              var list =  getAllRecord(-1)
                list.forEach {
                    it.favorite = true
                }
                view.showError(failure)
                view.setupList(list)

            })
    }

    fun selectFavorites() {
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