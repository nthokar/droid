package com.example.myapplication.services

import com.example.myapplication.data.Movie

interface MovieFinder {

    fun findMovie(movieId: Int) : Movie?

}