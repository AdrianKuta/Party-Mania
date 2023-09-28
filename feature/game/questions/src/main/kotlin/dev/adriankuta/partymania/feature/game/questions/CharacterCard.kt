package dev.adriankuta.partymania.feature.game.questions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.adriankuta.partymania.data.model.Character
import dev.adriankuta.partymania.core.ui.theme.PartyManiaTheme

@Composable
fun CharacterCard(
    character: Character,
    currentCharacterIndex: Int,
    modifier: Modifier = Modifier
) {
    Content(modifier, currentCharacterIndex, character)
}

@Composable
private fun Content(
    modifier: Modifier,
    currentCharacterIndex: Int,
    character: Character
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
            ) {
                Text(
                    text = "Runda",
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = "${currentCharacterIndex + 1}/${Config.RandomEntries}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = character.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium
            )

            character.category?.let { cat ->
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    text = cat,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(widthDp = 256, heightDp = 256)
@Composable
private fun CardPreview() {
    PartyManiaTheme {
        CharacterCard(
            modifier = Modifier.fillMaxSize(),
            currentCharacterIndex = 0,
            character = Character("Kubuś Puchatek", "Kubuś Puchatek")
        )
    }
}