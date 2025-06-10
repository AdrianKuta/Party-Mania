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
    onGameSelect: (GameType) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onGameSelect = onGameSelect,
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onGameSelect: (GameType) -> Unit,
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
                onClick = { onGameSelect(type) },
            )
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun GameButton(
    gameType: GameType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GameButton(
        text = gameName(gameType),
        containerColor = buttonColor(gameType),
        onClick = onClick,
        modifier = modifier,
    )
}

private fun gameName(gameType: GameType) = when (gameType) {
    GameType.Truth -> "\uD83D\uDE4A Truth"
    GameType.Random -> "\uD83C\uDFB2 Random"
    GameType.Challenge -> "\uD83C\uDFAF Challenge"
}

@Suppress("MagicNumber")
private fun buttonColor(gameType: GameType) = when (gameType) {
    GameType.Truth -> Color(0xFF6750A4)
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
                    GameType.Truth,
                    GameType.Challenge,
                    GameType.Random,
                ).toImmutableList(),
            ),
            onGameSelect = {},
        )
    }
}
