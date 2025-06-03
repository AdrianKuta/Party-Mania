package dev.adriankuta.partymania.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme
import dev.adriankuta.partymania.ui.gametype.GameType

@Composable
fun GameInfo(
    gameTypeUiInfo: GameTypeUIInfo?,
    modifier: Modifier = Modifier,
) {
    var lastUiInfo by remember { mutableStateOf(gameTypeUiInfo) }
    if (gameTypeUiInfo != null) {
        lastUiInfo = gameTypeUiInfo
    }

    AnimatedVisibility(
        visible = gameTypeUiInfo != null,
        enter = slideInVertically(
            animationSpec = tween(delayMillis = 250),
        ) + fadeIn(animationSpec = tween(delayMillis = 250)),
        exit = fadeOut() + shrinkVertically(),
        modifier = modifier,
    ) {
        lastUiInfo?.let { uiInfo ->
            Column {
                Text(
                    text = stringResource(id = uiInfo.title),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(id = uiInfo.description),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun GameInfoPreview() {
    PartyManiaTheme {
        GameInfo(
            gameTypeUiInfo = GameTypeUIInfo(GameType.CHALLENGE),
        )
    }
}
