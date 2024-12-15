package com.wellaxsa.domain.usecase

import com.wellaxsa.data.models.Game
import com.wellaxsa.data.utils.ResultWrapper
import com.wellaxsa.domain.repository.GameRepository
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(private val repository: GameRepository) {
    suspend operator fun invoke(): ResultWrapper<List<Game>> {
        return repository.getGames()
    }
}
