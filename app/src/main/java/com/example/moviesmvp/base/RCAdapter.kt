package com.example.moviesmvp.base

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.moviesmvp.R
import com.example.moviesmvp.data.model.Movie
import kotlinx.android.synthetic.main.movie_layout.view.*

class RCAdapter() : RecyclerView.Adapter<RCAdapter.Holder>() {


    private var isFinished = false
    var data = mutableListOf<Movie>()
    var onItemClick: ((Movie,View) -> Unit) ={movie, view ->  }

    companion object {
        const val HOLDER_MOVIE_TYPE = 1
        const val HOLDER_LOADING_TYPE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < data.size -> HOLDER_MOVIE_TYPE
            else -> HOLDER_LOADING_TYPE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return when (viewType) {
            HOLDER_MOVIE_TYPE -> VH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.movie_layout,
                    parent,
                    false
                )
            )
            else -> LoadingHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.movie_loading,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = data.size + if (isFinished) 0 else 1


    override fun onBindViewHolder(holder: Holder, position: Int) {
        data.getOrNull(position)?.let { holder.render(it) }
    }

    fun setData(movies: List<Movie>, isFinished: Boolean = false) {
        this.data = movies.toMutableList()
        this.isFinished = isFinished
        notifyDataSetChanged()
    }

    fun add(movies: List<Movie>, isFinished: Boolean = false) {
        this.isFinished = isFinished
        val positionStart = data.size
        data.addAll(movies)
        val positionEnd = data.size + if (isFinished) 0 else 1
        notifyItemRangeChanged(positionStart, positionEnd)
    }

    fun positionBy(movie: Movie) =
        data.indexOf(movie)



    abstract inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun render(movie: Movie?) {}
    }

    inner class VH(itemView: View) : Holder(itemView) {
        init {
            itemView.setOnClickListener {
                data.getOrNull(adapterPosition)?.let { movie ->
                    onItemClick(movie,itemView)

                }
            }
        }

        override fun render(movie: Movie?) {
            if (movie == null) return

            if(movie.favorite){
                itemView.favorite.setImageResource(R.drawable.favoriteon)
            }else{
                itemView.favorite.setImageResource(R.drawable.favoriteoff)

            }

            if (movie.image != "") {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/${movie.image}")
                    .into(itemView.imageMovie)
            } else {
                //itemView.imageMovi
            }



            itemView.titleMovie.text = movie.title
            itemView.dateMovie.text = datePatterBr(movie.date)
            itemView.synopsis.text = movie.synopsis

        }

        fun datePatterBr(date: String): String {
            val dateBr = date.split("-")
            return "${dateBr[2]}/${dateBr[1]}/${dateBr[0]}"
        }
    }

    inner class LoadingHolder(itemView: View) : Holder(itemView)

}