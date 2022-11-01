package com.hirocode.movie.ui.genres

import androidx.lifecycle.*
import com.hirocode.movie.data.GenreRepository
import com.hirocode.movie.di.Injection
import com.hirocode.movie.network.GenresItem
import kotlinx.coroutines.launch

class GenresViewModel(private val genreRepository: GenreRepository) : ViewModel() {

    private val _genres = MutableLiveData<List<GenresItem>>()
    var genres: LiveData<List<GenresItem>> = _genres

    fun getGenres() {
        viewModelScope.launch {
            _genres.postValue(genreRepository.getGenres())
        }
    }
}

class GenresViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GenresViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GenresViewModel(Injection.provideGenreRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
