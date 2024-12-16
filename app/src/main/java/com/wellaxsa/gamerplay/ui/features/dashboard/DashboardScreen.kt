package com.wellaxsa.gamerplay.ui.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wellaxsa.domain.models.Game
import com.wellaxsa.gamerplay.utills.PlatformFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.wellaxsa.gamerplay.R

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(R.drawable.ic_splash_logo)
                                .crossfade(true)
                                .build(),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(40.dp)
                        )
                        Text(
                            text = "GamerPlay Dashboard",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Red
                        )
                    }
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                colors = TopAppBarDefaults.mediumTopAppBarColors()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBar(
                    searchText = searchText,
                    onSearchTextChanged = { viewModel.onSearchTextChanged(it) }
                )
                FilterRow(
                    availablePlatforms = availablePlatforms,
                    selectedPlatform = selectedPlatform,
                    onPlatformSelected = { viewModel.onPlatformSelected(it) }
                )

                when {
                    errorMessage != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    isLoading && games.isEmpty() -> {
                        ShimmerGrid(itemCount = 6)
                    }
                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(games) { game ->
                                GameItem(game = game) {
                                    // Navigate to detail screen
                                }
                            }
                        }
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        placeholder = { Text("Search games") },
        singleLine = true
    )
}

@Composable
fun FilterRow(
    availablePlatforms: List<PlatformFilter>,
    selectedPlatform: PlatformFilter,
    onPlatformSelected: (PlatformFilter) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(availablePlatforms.size) { index ->
            val platform = availablePlatforms[index]
            FilterChip(
                selected = selectedPlatform == platform,
                onClick = { onPlatformSelected(platform) },
                label = { Text(platform.label) },
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun GameItem(game: Game, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(game.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "Game Thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Text(
                text = game.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
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
            ShimmerItem()
        }
    }
}

@Composable
fun ShimmerItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shimmerEffect()
    )
}

fun Modifier.shimmerEffect(): Modifier = this.background(
    brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )
    )
)
