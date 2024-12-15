package com.wellaxsa.gamerplay.ui.features.dashboard


import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.wellaxsa.domain.models.Game
import com.wellaxsa.gamerplay.utills.PlatformFilter


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val games = viewModel.games.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val searchText = viewModel.searchText.collectAsState().value
    val selectedPlatform = viewModel.selectedPlatform.collectAsState().value
    val availablePlatforms = viewModel.availablePlatforms

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.refresh() }
    )
    Box(Modifier.pullRefresh(pullRefreshState)) {
        Column(Modifier.fillMaxSize()) {
            SearchBar(searchText, onSearchTextChanged = { viewModel.onSearchTextChanged(it) })
            FilterRow(
                availablePlatforms = availablePlatforms,
                selectedPlatform = selectedPlatform,
                onPlatformSelected = { viewModel.onPlatformSelected(it) }
            )

            if (errorMessage != null) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            } else {
                if (isLoading && games.isEmpty()) {
                    ShimmerGrid(itemCount = 6)
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(games) { game ->
                            GameItem(game = game) {
                                // navController.navigate("dashboardDetail/${game.id}")
                            }
                        }
                    }
                }
            }
        }
        PullRefreshIndicator( // Place the indicator inside the Box
            refreshing = isLoading,
            state = pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        placeholder = { Text("Search games") }
    )
}

@Composable
fun FilterRow(
    availablePlatforms: List<PlatformFilter>,
    selectedPlatform: PlatformFilter,
    onPlatformSelected: (PlatformFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        availablePlatforms.forEach { platform ->
            FilterChip(
                selected = selectedPlatform == platform,
                onClick = { onPlatformSelected(platform) },
                label = { Text(platform.label) },
            )
        }
    }
}

@Composable
fun GameItem(game: Game, onClick: () -> Unit) {
    Text(text = game.title)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(game.thumbnail)
            .crossfade(true)
            .build(),
        contentDescription = "Game Thumbnail"
    )
}

@Composable
fun ShimmerGrid(itemCount: Int) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(itemCount) {
            ShimmerItem() // Placeholder shimmer item
        }
    }
}

@Composable
fun ShimmerItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shimmerEffect() // Apply the shimmer effect
    )
}

@Composable
fun Modifier.shimmerEffect(): Modifier = this.background(
    brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        ),
        start = androidx.compose.ui.geometry.Offset(10f, 10f),
        end = androidx.compose.ui.geometry.Offset(300f, 300f)
    )
)
