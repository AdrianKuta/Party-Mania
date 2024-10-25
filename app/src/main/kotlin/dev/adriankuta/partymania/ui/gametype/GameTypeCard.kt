package dev.adriankuta.partymania.ui.gametype

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme
import dev.adriankuta.partymania.core.ui.theme.Elevation
import dev.adriankuta.partymania.screens.home.GameTypeUIInfo

@Composable
fun GameTypeCard(
    gameCardUiInfo: GameTypeUIInfo,
    modifier: Modifier = Modifier,
    onTypeSelected: (GameTypeUIInfo) -> Unit = {}
) {
    Card(
        modifier = modifier
            .clickable { onTypeSelected(gameCardUiInfo) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = Elevation.Card
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(id = gameCardUiInfo.title),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(
    widthDp = 128,
    heightDp = 128,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun GameTypeCardPreview() {
    PartyManiaTheme {
        GameTypeCard(
            GameTypeUIInfo(GameType.CHALLENGE)
        )
    }
}

@Preview(
    widthDp = 128,
    heightDp = 128,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun GameTypeCardPreviewDarl() {
    PartyManiaTheme {
        GameTypeCard(
            GameTypeUIInfo(GameType.CHALLENGE)
        )
    }
}