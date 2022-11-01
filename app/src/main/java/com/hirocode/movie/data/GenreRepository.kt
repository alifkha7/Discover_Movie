package com.hirocode.movie.data

import com.hirocode.movie.BuildConfig.API_KEY
import com.hirocode.movie.network.ApiService
import com.hirocode.movie.network.GenresItem

class GenreRepository(private val apiService: ApiService) {
    suspend fun getGenres(): List<GenresItem> {
        return apiService.getGenresMovie(API_KEY).genres
    }
}