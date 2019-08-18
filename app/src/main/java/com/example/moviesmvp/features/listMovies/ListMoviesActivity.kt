package com.example.moviesmvp.features.listMovies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.moviesmvp.R
import com.example.moviesmvp.base.RCAdapter
import com.example.moviesmvp.data.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ListMoviesActivity : AppCompatActivity(), ListMoviesContract.View {
    override val presenter by inject<ListMoviesPresenter> { parametersOf(this) }
    private var loadListLocked = false
    private val adapter by lazy {
        RCAdapter()
    }
    private lateinit var llm: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llm = LinearLayoutManager(this)
        recyclerView.layoutManager = llm
        recyclerView.adapter = adapter
        setupToolbar()
        scrollLoading()
        presenter.loadMore()

    }


    override fun setupList(list: List<Movie>) {

        if (adapter.data.size > 0) {
            adapter.add(list)
        } else {
           adapter.setData(list)
        }


    }

    private fun setupToolbar() {
        toolbar
    }

    override fun onFinishLoad() {
        this.loadListLocked = true
    }

    fun scrollLoading() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (loadListLocked) return
                val lastVisibleMoviePosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if ((lastVisibleMoviePosition + (presenter.sizePage/2)) > adapter.itemCount) {
                    presenter.loadMore()
                }
            }
        })
    }
}
