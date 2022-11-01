package com.hirocode.movie.data

import com.hirocode.movie.BuildConfig.API_KEY
import com.hirocode.movie.network.ApiService
import com.hirocode.movie.network.VideosItem

class VideoRepository(private val apiService: ApiService) {
    suspend fun getVideos(movieId: Int?): List<VideosItem> {
        return apiService.getVideosMovie(movieId, API_KEY).results
    }
}