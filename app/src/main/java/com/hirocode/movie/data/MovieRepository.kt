package com.hirocode.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.hirocode.movie.network.ApiService
import com.hirocode.movie.network.DiscoverMovieItem

class MovieRepository(private val apiService: ApiService) {
    fun getDiscoverMovie(genreId: Int?): LiveData<PagingData<DiscoverMovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService, genreId)
            }
        ).liveData
    }
}