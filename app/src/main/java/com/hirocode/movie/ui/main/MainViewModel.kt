package com.hirocode.movie.ui.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hirocode.movie.data.MovieRepository
import com.hirocode.movie.di.Injection
import com.hirocode.movie.network.DiscoverMovieItem

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    lateinit var movie: LiveData<PagingData<DiscoverMovieItem>>

    fun getGenreId(genreId: Int?) {
        movie = movieRepository.getDiscoverMovie(genreId).cachedIn(viewModelScope)
    }
}

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideMovieRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}