package com.example.moviesmvp.data.local

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.moviesmvp.base.BaseDAO
import com.example.moviesmvp.data.model.Movie

class MovieDAO(context: Context) : BaseDAO(context) {

    override val query: String
        get() = MOVIE_DATA.query

    fun insert(movie: Movie): Boolean {
        val db = writableDatabase
        var result = true
        try {
            db.insert(MOVIE_DATA.TABLE_NAME, null, objectToContentValues(movie))
        } catch (e: Exception) {
            result = false
        } finally {
            db.close()
        }
        return result
    }

    fun getMovies(page: Int): List<Movie> {
        var seletion = "${MOVIE_DATA.PAGE} =?"
        var selectionArgs = arrayOf("$page")
        if (page < 0) {
            seletion = ""
            selectionArgs = emptyArray()
        }

        val db = writableDatabase
        val list = mutableListOf<Movie>()
        val columns = arrayOf(
            MOVIE_DATA.ID_MOVIE,
            MOVIE_DATA.TITLE,
            MOVIE_DATA.DATE,
            MOVIE_DATA.IMAGE,
            MOVIE_DATA.SYNOPSIS,
            MOVIE_DATA.PAGE
        )

        val cursor =
            db.query(
                MOVIE_DATA.TABLE_NAME,
                columns,
                seletion,
                selectionArgs,
                null,
                null,
                null
            )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(
                    Movie(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3) ?: "",
                        cursor.getString(4),
                        cursor.getInt(5)
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun deleteMovie(idMovie: Int) {
        val db = writableDatabase
        db.delete(
            MOVIE_DATA.TABLE_NAME,
            "${MOVIE_DATA.ID_MOVIE} = $idMovie",
            null
        )
        db.close()

    }

    fun getMovie(idMovie: Int): List<Int> {
        val db = writableDatabase
        val list = mutableListOf<Int>()
        val cursor = db.query(
            MOVIE_DATA.TABLE_NAME,
            arrayOf(MOVIE_DATA.ID_MOVIE),
            "${MOVIE_DATA.ID_MOVIE} =?",
            arrayOf("$idMovie"),
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(cursor.getInt(0))

            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun objectToContentValues(movie: Movie): ContentValues {
        val values = ContentValues()
        values.put(MOVIE_DATA.ID_MOVIE, movie.id)
        values.put(MOVIE_DATA.TITLE, movie.title)
        values.put(MOVIE_DATA.DATE, movie.date)
        values.put(MOVIE_DATA.IMAGE, movie.image)
        values.put(MOVIE_DATA.SYNOPSIS, movie.synopsis)
        values.put(MOVIE_DATA.PAGE, movie.moviePage)
        return values
    }
}