package com.example.moviesmvp.base

interface BaseView<out T: BasePresenter<*>> {
    val presenter: T
}