package com.example.myapplication.screens

import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Movie
import androidx.activity.viewModels


class MoviesViewModel : ViewModel() {
    val movies = mutableStateListOf<Movie>()
    var scrollPosition = 0

    init {
        for (i in 0..9) {
            movies.add(Movie(i, "Фильм $i", 2023 + i))
        }
    }

}