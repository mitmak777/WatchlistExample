package com.example.watchlistexample.di

import com.example.watchlistexample.data.datasource.ForexAPIService
import com.example.watchlistexample.data.datasource.LocalFxRealTimeUpdateDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
object ForexWatchlistModule {

    @Provides
    fun provideForexAPIService(retrofit: Retrofit): ForexAPIService {
        return retrofit.create(ForexAPIService::class.java)
    }

    @Provides
    fun provideLocalFxRealTimeUpdateDatasource(): LocalFxRealTimeUpdateDatasource {
        return LocalFxRealTimeUpdateDatasource()
    }
}

