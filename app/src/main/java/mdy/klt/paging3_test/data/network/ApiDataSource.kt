package mdy.klt.paging3_test.data.network

import mdy.klt.paging3_test.model.Movie
import mdy.klt.paging3_test.model.ResponseMovies
import retrofit2.Response

interface ApiDataSource {
    suspend fun fetchMovies(page: Int): Response<ResponseMovies>
    suspend fun fetchDetail(id: Int, lang: String): Response<Movie>
}