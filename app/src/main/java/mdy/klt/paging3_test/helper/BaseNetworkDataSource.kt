package mdy.klt.paging3_test.helper

import retrofit2.Response

abstract class BaseNetworkDataSource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {

        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body() ?: return Resource.error("EMPTY BODY")
                return Resource.success(body)
            }
            return Resource.error("ERROR CODE ${response.code()} : ${response.message()}")

        } catch (e: Exception) {
            return Resource.error(e.message ?: e.toString())
        }

    }
}