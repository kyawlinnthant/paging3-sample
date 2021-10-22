package mdy.klt.paging3_test.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import mdy.klt.paging3_test.data.network.ApiService
import mdy.klt.paging3_test.helper.Endpoints
import mdy.klt.paging3_test.model.Movie
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val apiService: ApiService

) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        var pageNumber = params.key ?: 1
        return try {
            val response = apiService.fetchMovies(Endpoints.API_KEY, pageNumber)
            val pageResponse = response.body()
            val data = pageResponse?.list

            //todo check for the last page
            var nextPageNumber: Int? = null

            pageResponse?.list?.let {
                nextPageNumber = pageNumber++
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = pageNumber++
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
}