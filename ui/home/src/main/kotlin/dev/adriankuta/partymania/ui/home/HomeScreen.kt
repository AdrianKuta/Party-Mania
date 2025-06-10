package dev.adriankuta.partymania.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme
import dev.adriankuta.partymania.core.designsystem.theme.PreviewDevices
import dev.adriankuta.partymania.domain.gametypes.entities.GameType
import dev.adriankuta.partymania.ui.home.components.GameButton
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(uiState = uiState)
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(8.dp),
    ) {
        uiState.games.forEachIndexed { index, type ->
            GameButton(
                gameType = type,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
            )
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun GameButton(
    gameType: GameType,
    modifier: Modifier = Modifier,
) {
    GameButton(
        text = gameName(gameType),
        containerColor = buttonColor(gameType),
        modifier = modifier,
    )
}

private fun gameName(gameType: GameType) = when (gameType) {
    GameType.TruthOrDare -> "\uD83D\uDE4A Truth or Dare"
    GameType.Random -> "\uD83C\uDFB2 Random"
    GameType.Challenge -> "\uD83C\uDFAF Challenge"
}

@Suppress("MagicNumber")
private fun buttonColor(gameType: GameType) = when (gameType) {
    GameType.TruthOrDare -> Color(0xFF6750A4)
    GameType.Random -> Color(0xFF625B71)
    GameType.Challenge -> Color(0xFF7D5260)
}

@PreviewDevices
@Composable
private fun HomeScreenPreview() {
    PartyManiaTheme {
        HomeScreen(
            uiState = HomeUiState(
                games = listOf(
                    GameType.TruthOrDare,
                    GameType.Random,
                    GameType.Challenge,
                ).toImmutableList(),
            ),
        )
    }
}
