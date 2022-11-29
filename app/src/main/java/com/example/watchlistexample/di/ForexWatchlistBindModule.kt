package com.example.watchlistexample.di

import com.example.watchlistexample.data.interfaces.ForexListRepo
import com.example.watchlistexample.data.repo.ForexListRepoImpl
import com.example.watchlistexample.domain.ForexWatchlistUseCase
import com.example.watchlistexample.domain.ForexWatchlistUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ForexWatchlistBindModule{
    @Binds
    abstract fun bindForexWatchlistUseCase(forexWatchlistUseCaseImpl: ForexWatchlistUseCaseImpl): ForexWatchlistUseCase

    @Binds
    abstract fun bindForexListRepo(forexListRepoImpl: ForexListRepoImpl): ForexListRepo
}