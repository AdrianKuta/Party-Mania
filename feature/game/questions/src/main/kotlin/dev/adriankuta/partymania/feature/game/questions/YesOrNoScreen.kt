@file:OptIn(ExperimentalMaterial3Api::class)

package dev.adriankuta.partymania.feature.game.questions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.adriankuta.partymania.core.ui.ConfirmQuitGameDialog
import dev.adriankuta.partymania.core.ui.GameEndedDialog
import dev.adriankuta.partymania.core.ui.GameTopBar
import dev.adriankuta.partymania.core.ui.NextPlayerPrompt
import dev.adriankuta.partymania.core.ui.theme.Elevation
import dev.adriankuta.partymania.core.ui.theme.PartyManiaTheme
import dev.adriankuta.partymania.data.model.Character
import kotlin.math.min

@Composable
fun YesOrNoScreen(
    modifier: Modifier = Modifier,
    viewModel: YesOrNoViewModel = hiltViewModel(),
    onGameEnd: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            GameTopBar(
                gameName = R.string.game_type_yes_or_no,
                closeGame = viewModel::onQuitGame
            )
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        BackHandler {
            viewModel.onQuitGame()
        }

        if (uiState.isQuitByUser) {
            ConfirmQuitGameDialog(
                onConfirm = viewModel::confirmQuitGame,
                onDismiss = viewModel::cancelQuitGame
            )
        }

        if (uiState.showNextPlayerPrompt) {
            NextPlayerPrompt {
                viewModel.onNextCharacter()
            }
        }

        if (uiState.isGameEnd) {
            GameEndedDialog(uiState.scoredPoints) {
                onGameEnd()
            }
        }

        if (uiState.characters.isNotEmpty()) {
            GameContent(
                modifier = Modifier.padding(paddingValues),
                points = uiState.scoredPoints,
                currentCharacterIndex = uiState.currentCharacterIndex,
                characters = uiState.characters,
                questionsLeft = uiState.questionsLeft ?: 0,
                onCountChange = viewModel::onAnswersChange,
                onNextQuestion = viewModel::onNextCharacter
            )
        }
    }
}

@Composable
private fun GameContent(
    modifier: Modifier = Modifier,
    points: Int,
    currentCharacterIndex: Int = 0,
    characters: List<Character>,
    questionsLeft: Int,
    onCountChange: (diff: Int) -> Unit,
    onNextQuestion: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        GamePoints(
            scoredPoints = points
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val dismissState = rememberDismissState(
                confirmValueChange = {
                    onNextQuestion()
                    false
                },
            )
            Text(
                text = "Postać do odgadnięcia",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.Start)
            )
            CardDeck(
                modifier = Modifier.weight(1f),
                currentIndex = currentCharacterIndex,
                characters = characters
            )

            Spacer(modifier = Modifier.height(24.dp))

            NextCharacterButton(
                onNextCharacter = onNextQuestion
            )

            Spacer(modifier = Modifier.height(24.dp))

            NewQuestionCount(
                questionsLeft = questionsLeft,
                onCountChange = onCountChange
            )

        }
    }
}

@Composable
fun CardDeck(
    modifier: Modifier = Modifier,
    currentIndex: Int,
    characters: List<Character>
) {
    val visibleCards = min(3, characters.size - currentIndex)
    val scope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        repeat(visibleCards) { idx ->
            var moved by remember { mutableStateOf(false) }
            val index = currentIndex + idx
            val character = characters[index]
            val zIndex = 100f - idx
            val offset by animateIntOffsetAsState(
                targetValue = if (moved) IntOffset(
                    500,
                    0
                ) else IntOffset.Zero,
                label = "Animate offset",
                animationSpec = tween(1000)
            )

            CharacterCard(
                modifier = Modifier
                    .zIndex(zIndex)
                    .offset { offset }
                    .clickable {
                        moved = !moved
                    },
                character = character,
                currentCharacterIndex = index
            )
        }
    }
}

@Composable
fun GamePoints(
    modifier: Modifier = Modifier,
    scoredPoints: Int = 0
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        shadowElevation = Elevation.AppBar,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.scored_points, scoredPoints),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun NextCharacterButton(
    modifier: Modifier = Modifier,
    onNextCharacter: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        tonalElevation = Elevation.Surface,
        shape = RoundedCornerShape(4.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp),
        ) {
            Column(
                Modifier.weight(1f)
            ) {
                Text(text = "Udało się odgadnąć postać?")
                Text(
                    text = "Liczba zadanych pytań ma wpływ na punktację",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onNextCharacter,
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.next_character),
                    overflow = TextOverflow.Visible,
                    softWrap = false
                )
            }
        }

    }

}

@Composable
fun QuestionCount(questionsLeft: Int) {
    Text(text = buildAnnotatedString {
        append(stringResource(id = R.string.questionsLeft) + ": ")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append(questionsLeft.toString())
        }
    })
}


@Preview(showBackground = true)
@Composable
private fun GameContentPreview() {
    PartyManiaTheme {
        GameContent(
            characters = listOf(
                Character("Kubuś Puchatek", "Kubuś Puchatek"),
                Character("Kubuś Puchatek", "Kubuś Puchatek")
            ),
            points = 25,
            questionsLeft = 20,
            onCountChange = {},
            onNextQuestion = {})
    }
}
