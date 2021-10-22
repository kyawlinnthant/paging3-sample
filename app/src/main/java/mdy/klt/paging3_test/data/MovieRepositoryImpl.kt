package mdy.klt.paging3_test.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mdy.klt.paging3_test.data.network.ApiDataSource
import mdy.klt.paging3_test.data.network.ApiService
import mdy.klt.paging3_test.di.IoDispatcher
import mdy.klt.paging3_test.helper.BaseNetworkDataSource
import mdy.klt.paging3_test.helper.Resource
import mdy.klt.paging3_test.model.Movie
import mdy.klt.paging3_test.paging.MovieDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository, BaseNetworkDataSource() {

    override suspend fun getMoviesList(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 25, prefetchDistance = 2),
            pagingSourceFactory = { MovieDataSource(apiService) }
        ).flow
    }

    override suspend fun getMovieDetail(id: Int, lang: String): Flow<Resource<Movie>> {
        return flow {
            emit(safeApiCall { apiDataSource.fetchDetail(id, lang) })
        }.flowOn(ioDispatcher)
    }

}