package com.wellaxsa.gamerplay.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellaxsa.domain.models.Game
import com.wellaxsa.domain.usecase.GamesUseCase
import com.wellaxsa.domain.utils.ResultWrapper
import com.wellaxsa.gamerplay.utills.PlatformFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val gamesUseCase: GamesUseCase
) : ViewModel() {

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _selectedPlatform = MutableStateFlow<PlatformFilter>(PlatformFilter.All)
    val selectedPlatform: StateFlow<PlatformFilter> = _selectedPlatform

    val availablePlatforms = listOf(PlatformFilter.All, PlatformFilter.PC, PlatformFilter.Browser)

    init {
        fetchGames()
    }

    fun fetchGames() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = gamesUseCase()) {
                is ResultWrapper.Success -> _games.value = result.data
                is ResultWrapper.Error -> _errorMessage.value = result.message
            }
            _isLoading.value = false
        }
    }

    fun refresh() {
        fetchGames()
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
        filterGames()
    }

    fun onPlatformSelected(platform: PlatformFilter) {
        _selectedPlatform.value = platform
        filterGames()
    }

    private fun filterGames() {
        val filteredGames = _games.value.filter { game ->
            val searchTextMatches = searchText.value.isEmpty() || game.title.contains(searchText.value, ignoreCase = true)
            val platformMatches = selectedPlatform.value == PlatformFilter.All || game.platform.contains(selectedPlatform.value.label, ignoreCase = true)
            searchTextMatches && platformMatches
        }
        _games.value = filteredGames
    }
}
