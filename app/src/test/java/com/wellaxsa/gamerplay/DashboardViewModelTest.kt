package com.wellaxsa.gamerplay

import com.wellaxsa.domain.models.Game
import com.wellaxsa.domain.usecase.GamesUseCase
import com.wellaxsa.domain.utils.ResultWrapper
import com.wellaxsa.gamerplay.ui.features.dashboard.DashboardViewModel
import com.wellaxsa.gamerplay.utills.PlatformFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @Mock
    private lateinit var gamesUseCase: GamesUseCase
    @InjectMocks
    private lateinit var viewModel: DashboardViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(gamesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchGames success`() = runTest {
        val gamesList = listOf(Game(title = "Game 1"), Game(title = "Game 2"))
        whenever(gamesUseCase()).doReturn(ResultWrapper.Success(gamesList))

        viewModel.fetchGames()

        assertEquals(gamesList, viewModel.games.first())
        assertEquals(false, viewModel.isLoading.first())
        assertEquals(null, viewModel.errorMessage.first())
    }

    @Test
    fun `fetchGames error`() = runTest {
        val errorMessage = "Error message"
        whenever(gamesUseCase()).doReturn(ResultWrapper.Error(errorMessage))

        viewModel.fetchGames()

        assertEquals(false, viewModel.isLoading.first())
        assertEquals(errorMessage, viewModel.errorMessage.first())
    }

    @Test
    fun `onSearchTextChanged filters games by title`() = runTest {
        val gamesList = listOf(
            Game(title = "Game 1"),
            Game(title = "Game 2"),
            Game(title = "Other Game")
        )
        viewModel.originalGamesList = gamesList

        viewModel.onSearchTextChanged("game")

        assertEquals(listOf(gamesList[0], gamesList[1]), viewModel.games.first())
    }

    @Test
    fun `onPlatformSelected filters games by platform`() = runTest {
        val gamesList = listOf(
            Game(title = "Game 1", platforms = "PC"),
            Game(title = "Game 2", platforms = "Android"),
            Game(title = "Other Game", platforms = "iOS")
        )
        viewModel.originalGamesList = gamesList

        viewModel.onPlatformSelected(PlatformFilter.PC)

        assertEquals(listOf(gamesList[0]), viewModel.games.first())
    }

    @Test
    fun `filterGames with empty search and All platform returns all games`() = runTest {
        val gamesList = listOf(
            Game(title = "Game 1", platforms = "PC"),
            Game(title = "Game 2", platforms = "Android"),
            Game(title = "Other Game", platforms = "iOS")
        )
        viewModel.originalGamesList = gamesList

        viewModel.onSearchTextChanged("")
        viewModel.onPlatformSelected(PlatformFilter.All)

        assertEquals(gamesList, viewModel.games.first())
    }
}
