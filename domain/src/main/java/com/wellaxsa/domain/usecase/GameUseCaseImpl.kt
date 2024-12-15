package com.wellaxsa.domain.usecase


import com.wellaxsa.domain.models.Game
import com.wellaxsa.domain.utils.ResultWrapper
import com.wellaxsa.domain.repository.GameRepository
import javax.inject.Inject

class GameUseCase @Inject constructor(private val repository: GameRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<Game> {
        return repository.getGame(id)
    }
}
