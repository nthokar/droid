package com.example.myapplication.services.imp

import com.example.myapplication.data.Movie
import com.example.myapplication.services.MovieFinder

class MockMovieFinder() : MovieFinder {


    override fun findMovie(movieId: Int): Movie? {

        return Movie(movieId, "Dune", 2024, "https://avatars.mds.yandex.net/get-kinopoisk-image/9784475/0c67265b-6631-4e25-b89c-3ddf4e5a1ee7/1920x" , "Герцог Пол Атрейдес присоединяется к фременам, чтобы стать Муад Дибом, одновременно пытаясь остановить наступление войны.")

    }


}