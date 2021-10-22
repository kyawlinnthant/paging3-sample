package mdy.klt.paging3_test.data.network

import mdy.klt.paging3_test.helper.Endpoints
import mdy.klt.paging3_test.model.Movie
import mdy.klt.paging3_test.model.ResponseMovies
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : ApiDataSource {
    override suspend fun fetchMovies(page: Int): Response<ResponseMovies> {
        return apiService.fetchMovies(Endpoints.API_KEY, page)
    }

    override suspend fun fetchDetail(id: Int, lang: String): Response<Movie> {
        return apiService.fetchMovieDetail(id, Endpoints.API_KEY, lang)
    }
}