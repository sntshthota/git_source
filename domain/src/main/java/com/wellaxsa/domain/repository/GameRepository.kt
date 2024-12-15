package com.wellaxsa.domain.repository

import com.wellaxsa.data.models.Game
import com.wellaxsa.data.utils.ResultWrapper

interface GameRepository {
    suspend fun getGames(): ResultWrapper<List<Game>>
    suspend fun getGame(id:Int): ResultWrapper<Game>
}
