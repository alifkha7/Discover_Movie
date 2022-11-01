package com.hirocode.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.hirocode.movie.network.ApiService
import com.hirocode.movie.network.ReviewsItem

class ReviewRepository(private val apiService: ApiService) {
    fun getReviewsMovie(movieId: Int?): LiveData<PagingData<ReviewsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            pagingSourceFactory = {
                ReviewPagingSource(apiService, movieId)
            }
        ).liveData
    }
}