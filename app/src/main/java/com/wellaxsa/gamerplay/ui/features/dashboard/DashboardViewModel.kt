package com.wellaxsa.gamerplay.ui.features.dashboard

import com.wellaxsa.domain.usecase.GetGameUseCase
import com.wellaxsa.domain.usecase.GetGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val getGameUseCase: GetGameUseCase
) : ViewModel() {

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games

    private val _game = MutableStateFlow<Game?>(null)
    val game: StateFlow<Game?> = _game

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchGames()
    }

    fun fetchGames() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = getGamesUseCase()) {
                is NetworkResult.Success -> _games.value = result.data
                is NetworkResult.Error -> _errorMessage.value = result.message
            }
            _isLoading.value = false
        }
    }

    fun fetchGame(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = getGameUseCase(id)) {
                is NetworkResult.Success -> _game.value = result.data
                is NetworkResult.Error -> _errorMessage.value = result.message
            }
            _isLoading.value = false
        }
    }
}
