package mdy.klt.paging3_test.model

import com.google.gson.annotations.SerializedName

/*
 "page": 1,
 "results": [ {.....},...],
 "total_pages": 500,
 "total_results": 10000
 */


data class ResponseMovies(
    @SerializedName("results") val list: List<Movie>,
    val total_results: Double,
    val page: Int
)