package com.example.moviesmvp.data.remote

import android.util.Log
import com.example.moviesmvp.data.model.ListMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ServiceRequest(val service: ServiceProvider) {
 //   companion object {
          val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
          val LANGUAGE = "pt-BR"
   // }

    fun getNextPage(
        page: Int,
        success: (ListMovies) -> Unit,
        failure: (String) -> Unit
    ) {
        service.request.getmMovies(API_KEY, LANGUAGE, page)
            .enqueue(object : Callback<ListMovies> {
                override fun onFailure(call: Call<ListMovies>, t: Throwable) {
                    Log.i("request","failure ${t.message.toString()}")
                    failure(t.message.toString())
                }

                override fun onResponse(call: Call<ListMovies>, response: Response<ListMovies>) {
                    Log.i("request","success")
                    response.body()?.let(success)
                }
            })

    }
}