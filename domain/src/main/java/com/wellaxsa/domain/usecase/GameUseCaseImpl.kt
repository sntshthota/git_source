package com.wellaxsa.domain.usecase


import com.wellaxsa.data.models.Game
import com.wellaxsa.data.utils.ResultWrapper
import com.wellaxsa.domain.repository.GameRepository
import javax.inject.Inject

class GetGameUseCase @Inject constructor(private val repository: GameRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<Game> {
        return repository.getGame(id)
    }
}
