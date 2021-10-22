package mdy.klt.paging3_test.data.network

import mdy.klt.paging3_test.helper.Endpoints
import mdy.klt.paging3_test.model.Movie
import mdy.klt.paging3_test.model.ResponseMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Endpoints.GET_LIST_URL)
    suspend fun fetchMovies(
        @Query("api_key") key: String,
        @Query("page") pageNumber: Int
    ): Response<ResponseMovies>

    @GET(Endpoints.GET_DETAIL_URL)
    suspend fun fetchMovieDetail(
        @Path("id") movieId: Int,
        @Query("api_key") key: String,
        @Query("language") language: String
    ): Response<Movie>

}