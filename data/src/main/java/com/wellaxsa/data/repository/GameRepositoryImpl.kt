package com.wellaxsa.data.repository

import com.wellaxsa.data.models.Game
import com.wellaxsa.data.remote.ApiService
import com.wellaxsa.data.utils.ResultWrapper
import com.wellaxsa.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GameRepository {

    override suspend fun getGames(): ResultWrapper<List<Game>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = apiService.getGames()
            ResultWrapper.Success(response)
        } catch (ioException: IOException) {
            ResultWrapper.Error("Network Error: ${ioException.message}")
        } catch (httpException: HttpException) {
            ResultWrapper.Error("HTTP Error: ${httpException.code()}")
        } catch (e: Exception) {
            ResultWrapper.Error("Unknown Error: ${e.message}")
        }
    }

    override suspend fun getGame(id: Int): ResultWrapper<Game> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = apiService.getGame(id)
            ResultWrapper.Success(response)
        } catch (ioException: IOException) {
            ResultWrapper.Error("Network Error: ${ioException.message}")
        } catch (httpException: HttpException) {
            ResultWrapper.Error("HTTP Error: ${httpException.code()}")
        } catch (e: Exception) {
            ResultWrapper.Error("Unknown Error: ${e.message}")
        }
    }
}
