package mdy.klt.paging3_test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mdy.klt.paging3_test.data.MovieRepository
import mdy.klt.paging3_test.data.MovieRepositoryImpl
import mdy.klt.paging3_test.data.network.ApiDataSource
import mdy.klt.paging3_test.data.network.ApiService
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(apiDataSource: ApiDataSource, apiService: ApiService): MovieRepository =
        MovieRepositoryImpl(apiDataSource,apiService)
}