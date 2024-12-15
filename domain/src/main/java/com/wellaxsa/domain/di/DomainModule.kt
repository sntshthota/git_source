package com.wellaxsa.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class) // Use ViewModelComponent for ViewModels
object DomainModule {

    @Provides
    fun provideGetGamesUseCase(repository: GameRepository): GetGamesUseCase {
        return GetGamesUseCase(repository)
    }

    @Provides
    fun provideGetGameUseCase(repository: GameRepository): GetGameUseCase {
        return GetGameUseCase(repository)
    }
}
