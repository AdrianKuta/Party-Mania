package dev.adriankuta.partymania.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme
import dev.adriankuta.partymania.core.designsystem.theme.PreviewDevices
import dev.adriankuta.partymania.ui.home.components.GameButton

internal enum class GameType {
    TruthOrDare, Random, Challenge
}

internal data class UiState(
    val games: List<GameType>,
)

@Composable
internal fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    HomeScreen(uiState = uiState)
}

@Composable
private fun HomeScreen(
    uiState: UiState,
) {
    val colors = listOf(
        Color(0xFF6750A4),
        Color(0xFF625B71),
        Color(0xFF7D5260),
        Color(0xFF52635F),
        Color(0xFF7D5260),
        Color(0xFF52635F),
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(8.dp),
    ) {
        uiState.games.forEachIndexed { index, type ->
            GameButton(
                text = type.name,
                containerColor = colors[index % colors.size],
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
            )
        }
    }
}

@PreviewDevices
@Composable
private fun HomeScreenPreview() {
    PartyManiaTheme {
        HomeScreen(
            uiState = UiState(
                games = GameType.entries,
            ),
        )
    }
}
