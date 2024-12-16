package com.wellaxsa.data

import com.wellaxsa.data.remote.ApiService
import com.wellaxsa.data.repository.GameRepositoryImpl
import com.wellaxsa.domain.models.Game
import com.wellaxsa.domain.utils.ResultWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GameRepositoryImplTest {

    @Mock
    private lateinit var apiService: ApiService
    private lateinit var repository: GameRepositoryImpl

    @Before
    fun setup() {
        repository = GameRepositoryImpl(apiService)
    }

    @Test
    fun `getGames returns Success with game list`() = runTest {
        val mockGames = listOf(
            Game("Desc 1", "Game 1", "thumb1", 1, "url1", "genre1", "platform1", "pub1", "dev1", "date1","profileurl1"),
            Game("Desc 2", "Game 2", "thumb2", 2, "url2", "genre2", "platform2", "pub2", "dev2", "date2","profileurl2")
        )
        `when`(apiService.getGames()).thenReturn(mockGames)
        val result = repository.getGames()
        assertEquals(ResultWrapper.Success(mockGames), result)
    }

    @Test
    fun `getGames returns Error on HttpException`() = runTest {
        val errorCode = 404
        `when`(apiService.getGames()).thenThrow(retrofit2.HttpException(retrofit2.Response.error<Any>(errorCode, okhttp3.ResponseBody.create(null, ""))))
        val result = repository.getGames()
        assertEquals(ResultWrapper.Error("HTTP Error: $errorCode"), result)
    }

    @Test
    fun `getGames returns Error on Unknown Exception`() = runTest {
        val errorMessage = "Some unknown error"
        `when`(apiService.getGames()).thenThrow(RuntimeException(errorMessage))
        val result = repository.getGames()
        assertEquals(ResultWrapper.Error("Unknown Error: $errorMessage"), result)
    }

    @Test
    fun `getGame returns Success with game`() = runTest {
        val mockGame = Game("desc1", "Game 1", "thumb1", 1, "url1", "genre1", "platform1", "pub1", "dev1", "date1","profileurl1")
        val gameId = 1
        `when`(apiService.getGame(gameId)).thenReturn(mockGame)
        val result = repository.getGame(gameId)
        assertEquals(ResultWrapper.Success(mockGame), result)
    }

    @Test
    fun `getGame returns Error on IOException`() = runTest {
        val expectedErrorMessage = "Network Error: Network Error"
        val gameId = 1
        `when`(apiService.getGame(gameId)).thenThrow(RuntimeException(expectedErrorMessage))
        val result = repository.getGame(gameId)
        assertEquals(ResultWrapper.Error("Unknown Error: Network Error: Network Error"), result)
    }

    @Test
    fun `getGame returns Error on Unknown Exception`() = runTest {
        val errorMessage = "Some unknown error"
        val gameId = 1
        `when`(apiService.getGame(gameId)).thenThrow(RuntimeException(errorMessage))
        val result = repository.getGame(gameId)
        assertEquals(ResultWrapper.Error("Unknown Error: $errorMessage"), result)
    }
}
