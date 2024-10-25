package dev.adriankuta.partymania.core.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.adriankuta.partymania.core.common.Config
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme
import dev.adriankuta.partymania.core.model.Character

@Composable
fun CharacterCard(
    character: Character,
    currentCharacterIndex: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactions: MutableInteractionSource = remember { MutableInteractionSource() }

    Card(
        onClick = onClick,
        interactionSource = interactions,
        modifier = modifier
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
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    text = cat,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(widthDp = 256, heightDp = 256, showBackground = true)
@Composable
private fun CardPreview() {
    PartyManiaTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            CharacterCard(
                modifier = Modifier.fillMaxSize(),
                currentCharacterIndex = 0,
                character = Character("Kubuś Puchatek", "Kubuś Puchatek")
            )
        }
    }
}