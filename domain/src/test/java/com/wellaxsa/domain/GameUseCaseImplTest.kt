package com.wellaxsa.domain

import com.wellaxsa.domain.models.Game
import com.wellaxsa.domain.repository.GameRepository
import com.wellaxsa.domain.usecase.GamesUseCase
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
class GameUseCaseImplTest {

    @Mock
    private lateinit var repository: GameRepository
    private lateinit var getGamesUseCase: GamesUseCase

    @Before
    fun setup() {
        getGamesUseCase = GamesUseCase(repository)
    }

    @Test
    fun `GetGamesUseCase returns Success with game list`() = runTest {
        val mockGames = listOf(
            Game("Desc1", "Game 1", "thumb1", 1, "url1", "genre1", "platform1", "pub1", "dev1", "date1","profileurl1"),
            Game("Desc2", "Game 2", "thumb2", 1, "url2", "genre2", "platform2", "pub2", "dev2", "date2","profileurl2")
        )
        `when`(repository.getGames()).thenReturn(ResultWrapper.Success(mockGames))
        val result = getGamesUseCase()
        assertEquals(ResultWrapper.Success(mockGames), result)
    }

    @Test
    fun `GetGamesUseCase returns Error`() = runTest {
        val errorMessage = "Network Error"
        `when`(repository.getGames()).thenReturn(ResultWrapper.Error(errorMessage))
        val result = getGamesUseCase()
        assertEquals(ResultWrapper.Error(errorMessage), result)
    }
}
