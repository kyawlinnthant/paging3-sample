package mdy.klt.paging3_test.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mdy.klt.paging3_test.BuildConfig
import mdy.klt.paging3_test.data.network.ApiDataSource
import mdy.klt.paging3_test.data.network.ApiDataSourceImpl
import mdy.klt.paging3_test.data.network.ApiService
import mdy.klt.paging3_test.helper.Endpoints
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiDataSource(apiService: ApiService): ApiDataSource = ApiDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(GsonConverterFactory.create(gson))
        client(okHttpClient)
    }.build()

    @Provides
    @Singleton
    fun provideBaseUrl() = Endpoints.BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .serializeNulls()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(OkHttpProfilerInterceptor())
            .addNetworkInterceptor(OkHttpProfilerInterceptor())
            .build()
    } else OkHttpClient.Builder().build()
}