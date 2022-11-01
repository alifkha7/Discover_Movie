package com.hirocode.movie.ui.details

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hirocode.movie.data.ReviewRepository
import com.hirocode.movie.data.VideoRepository
import com.hirocode.movie.di.Injection
import com.hirocode.movie.network.ReviewsItem
import com.hirocode.movie.network.VideosItem
import kotlinx.coroutines.launch

class DetailsViewModel(private val reviewRepository: ReviewRepository, private val videoRepository: VideoRepository) : ViewModel() {

    lateinit var review: LiveData<PagingData<ReviewsItem>>

    private val _videos = MutableLiveData<List<VideosItem>>()
    var videos: LiveData<List<VideosItem>> = _videos

    fun getMovieId(movieId: Int?) {
        review = reviewRepository.getReviewsMovie(movieId).cachedIn(viewModelScope)
    }

    fun getVideos(movieId: Int?) {
        viewModelScope.launch {
            _videos.postValue(videoRepository.getVideos(movieId))
        }
    }
}

class DetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(Injection.provideReviewRepository(), Injection.provideVideosRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}