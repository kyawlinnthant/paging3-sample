package mdy.klt.paging3_test.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mdy.klt.paging3_test.helper.Resource
import mdy.klt.paging3_test.model.Movie

interface MovieRepository {
    suspend fun getMoviesList(): Flow<PagingData<Movie>>
    suspend fun getMovieDetail(id: Int, lang: String): Flow<Resource<Movie>>
}