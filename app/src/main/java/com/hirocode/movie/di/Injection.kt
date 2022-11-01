package com.hirocode.movie.di

import com.hirocode.movie.data.GenreRepository
import com.hirocode.movie.data.MovieRepository
import com.hirocode.movie.data.ReviewRepository
import com.hirocode.movie.data.VideoRepository
import com.hirocode.movie.network.ApiConfig

object Injection {
    fun provideGenreRepository(): GenreRepository {
        val apiService = ApiConfig.getApiService()
        return GenreRepository(apiService)
    }

    fun provideMovieRepository(): MovieRepository {
        val apiService = ApiConfig.getApiService()
        return MovieRepository(apiService)
    }

    fun provideReviewRepository(): ReviewRepository {
        val apiService = ApiConfig.getApiService()
        return ReviewRepository(apiService)
    }

    fun provideVideosRepository(): VideoRepository {
        val apiService = ApiConfig.getApiService()
        return VideoRepository(apiService)
    }
}