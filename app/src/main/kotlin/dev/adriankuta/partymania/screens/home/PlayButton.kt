package dev.adriankuta.partymania.screens.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.adriankuta.partymania.R
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme
import dev.adriankuta.partymania.ui.gametype.GameType

@Composable
fun PlayButton(
    uiState: HomeUiState,
    onClick: (gameType: GameType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        onClick = {
            uiState.selectedGameType?.let {
                onClick(it.gameType)
            }
        },
    ) {
        Text(text = stringResource(id = R.string.start_game))
    }
}

@Preview
@Composable
private fun PlayButtonPreview() {
    PartyManiaTheme {
        PlayButton(uiState = PreviewHomeUiState, onClick = { _ -> })
    }
}
