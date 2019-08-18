package com.example.moviesmvp.features.listMovies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.moviesmvp.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ListMoviesActivity : AppCompatActivity(),ListMoviesContract.View {
    override val presenter by inject<ListMoviesPresenter> { parametersOf(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
