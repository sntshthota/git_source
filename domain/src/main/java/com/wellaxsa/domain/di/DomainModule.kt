package com.wellaxsa.domain.di

import com.wellaxsa.domain.repository.GameRepository
import com.wellaxsa.domain.usecase.GamesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideGamesUseCase(repository: GameRepository): GamesUseCase {
        return GamesUseCase(repository)
    }

    @Provides
    fun provideGameUseCase(repository: GameRepository): GamesUseCase {
        return GamesUseCase(repository)
    }
}
