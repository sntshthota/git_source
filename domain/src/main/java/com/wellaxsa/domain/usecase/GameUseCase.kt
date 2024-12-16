package com.wellaxsa.domain.usecase

import com.wellaxsa.domain.models.Game
import com.wellaxsa.domain.utils.ResultWrapper
import com.wellaxsa.domain.repository.GameRepository
import javax.inject.Inject

interface GamesUseCase {
    suspend operator fun invoke(): ResultWrapper<List<Game>>
}
