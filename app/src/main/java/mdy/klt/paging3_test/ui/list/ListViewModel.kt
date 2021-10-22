package mdy.klt.paging3_test.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import mdy.klt.paging3_test.data.MovieRepository
import mdy.klt.paging3_test.model.Movie
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    init {
        Timber.tag("vm reach").e("here")
        getMovieList()
    }

    private lateinit var _movies: Flow<PagingData<Movie>>
    val movies: Flow<PagingData<Movie>> get() = _movies

    private fun getMovieList() {
        viewModelScope.launch {
            _movies = movieRepository.getMoviesList()
        }
    }

}