package com.wellaxsa.gamerplay

import com.wellaxsa.domain.models.Game
import com.wellaxsa.domain.usecase.GamesUseCase
import com.wellaxsa.domain.utils.ResultWrapper
import com.wellaxsa.gamerplay.ui.features.dashboard.DashboardViewModel
import com.wellaxsa.gamerplay.utills.PlatformFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var gamesUseCase: GamesUseCase
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        gamesUseCase = mock()
        viewModel = DashboardViewModel(gamesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchGames sets games on success`() = runTest {
        val mockGames = listOf(
            Game(id = 1, title = "Game 1", platforms = "PC"),
            Game(id = 2, title = "Game 2", platforms = "Android")
        )
        whenever(gamesUseCase()).thenReturn(ResultWrapper.Success(mockGames))

        viewModel.fetchGames()
        advanceUntilIdle()

        assertEquals(mockGames, viewModel.games.first())
        assertEquals(false, viewModel.isLoading.first())
        assertEquals(null, viewModel.errorMessage.first())
    }

    @Test
    fun `fetchGames sets errorMessage on failure`() = runTest {
        val errorMessage = "Error fetching games"
        whenever(gamesUseCase()).thenReturn(ResultWrapper.Error(errorMessage))

        viewModel.fetchGames()
        advanceUntilIdle()

        assertEquals(true, viewModel.isLoading.first())
        assertEquals(errorMessage, viewModel.errorMessage.first())
        assertEquals(emptyList<Game>(), viewModel.games.first())
    }

    @Test
    fun `onSearchTextChanged filters games based on search text`() = runTest {
        val mockGames = listOf(
            Game(id = 1, title = "Game 1", platforms = "PC"),
            Game(id = 2, title = "Game 2", platforms = "Android"),
            Game(id = 3, title = "Another Game", platforms = "PC")
        )
        whenever(gamesUseCase()).thenReturn(ResultWrapper.Success(mockGames))

        viewModel.fetchGames()
        advanceUntilIdle()

        viewModel.onSearchTextChanged("Game 1")
        advanceUntilIdle()

        val filteredGames = viewModel.games.first()
        assertEquals(1, filteredGames.size)
        assertEquals("Game 1", filteredGames.first().title)
    }

    @Test
    fun `onPlatformSelected filters games based on platform`() = runTest {
        val mockGames = listOf(
            Game(id = 1, title = "Game 1", platforms = "PC"),
            Game(id = 2, title = "Game 2", platforms = "Android"),
            Game(id = 3, title = "Another Game", platforms = "PC")
        )
        whenever(gamesUseCase()).thenReturn(ResultWrapper.Success(mockGames))

        viewModel.fetchGames()
        advanceUntilIdle()

        viewModel.onPlatformSelected(PlatformFilter.PC)
        advanceUntilIdle()

        val filteredGames = viewModel.games.first()
        assertEquals(2, filteredGames.size)
        assertEquals("PC", filteredGames.first().platforms)
    }
}

