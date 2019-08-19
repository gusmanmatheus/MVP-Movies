package com.example.moviesmvp.data.local

class MOVIE_DATA {
    companion object {
        const val TABLE_NAME = "MOVIES"
        const val TITLE = "TITLE"
        const val ID_MOVIE = "ID_MOVIE"
        const val ID = "_id"
        const val DATE = "DATE"
        const val SYNOPSIS = "SYNOPSIS"
        const val IMAGE = "IMAGE"
        const val PAGE = "PAGE"
        const val query = "create table $TABLE_NAME" +
                " ($ID integer primary key autoincrement," +
                " $ID_MOVIE integer not null," +
                " $TITLE text not null," +
                " $DATE text not null," +
                " $IMAGE text not null," +
                " $SYNOPSIS text not null," +
                " $PAGE text not null);"
    }
}

