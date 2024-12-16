package com.wellaxsa.domain.repository

import com.wellaxsa.domain.models.Game
import com.wellaxsa.domain.utils.ResultWrapper

interface GameRepository {
    suspend fun getGames(): ResultWrapper<List<Game>>
    suspend fun getGame(id:Int): ResultWrapper<Game>
}
