package com.wellaxsa.data.remote

import com.wellaxsa.domain.models.Game
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("giveaways")
    suspend fun getGames(): List<Game>

    @GET("giveaway?id={id}")
    suspend fun getGame(@Path("id") id: Int): Game
}
