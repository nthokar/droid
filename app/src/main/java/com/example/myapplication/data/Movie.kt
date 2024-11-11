package com.example.myapplication.data

import kotlin.time.Duration

data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val posterUrl: String? = null,
    val duration: String? = null,
    val rating: Double? = null,
    val description: String? = null
)