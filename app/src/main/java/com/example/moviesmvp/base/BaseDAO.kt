package com.example.moviesmvp.base

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviesmvp.R
import com.example.moviesmvp.data.local.MOVIE_DATA

abstract class BaseDAO
    (context: Context): SQLiteOpenHelper(
    context,
    context.resources.getString(R.string.name_db),
    null,
    context.resources.getInteger(R.integer.version_db)){

        abstract val query: String

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(query) //temos certeza que nao vai ser nulo, 'e obrigatori implementar a query
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL( "DROP TABLE IF EXISTS"+MOVIE_DATA.TABLE_NAME)
            onCreate(db)
        }
    }

