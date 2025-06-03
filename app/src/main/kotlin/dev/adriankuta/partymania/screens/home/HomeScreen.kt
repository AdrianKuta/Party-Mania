package dev.adriankuta.partymania.screens.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.adriankuta.partymania.core.designsystem.theme.ElevationTokens
import dev.adriankuta.partymania.ui.gametype.GameType
import timber.log.Timber

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onStartGame: (GameType) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        Timber.d("State: $uiState")
    }
    HomeScreenContent(
        modifier = modifier
            .fillMaxSize(),
        uiState = uiState,
        onStartGame = {
            onStartGame(it)
            viewModel.clearSelectedType()
        },
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    onStartGame: (GameType) -> Unit = {},
) {
    Surface(
        tonalElevation = ElevationTokens.Level3,
        modifier = modifier,
    ) {
        Column {
            MainMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                uiState = uiState,
                showOnlySelected = uiState.selectedGameType != null,
            )
            GameInfo(gameTypeUiInfo = uiState.selectedGameType)
            AnimatedVisibility(
                visible = uiState.selectedGameType != null,
                enter = expandVertically(
                    animationSpec = tween(delayMillis = 500),
                ),
            ) {
                PlayButton(
                    uiState = uiState,
                    onClick = onStartGame,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Preview()
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        uiState = PreviewHomeUiState.copy(
            selectedGameType = null,
        ),
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun HomeScreenGameSelectedPreview() {
    HomeScreenContent(
        uiState = PreviewHomeUiState,
    )
}

val PreviewHomeUiState = HomeUiState(
    gameTypes = listOf(
        GameTypeUIInfo(GameType.CHALLENGE),
        GameTypeUIInfo(GameType.RIDDLE),
        GameTypeUIInfo(GameType.IMPROVISATIONS),
        GameTypeUIInfo(GameType.YES_OR_NO),
    ),
    selectedGameType = GameTypeUIInfo(
        GameType.CHALLENGE,
    ),
)
