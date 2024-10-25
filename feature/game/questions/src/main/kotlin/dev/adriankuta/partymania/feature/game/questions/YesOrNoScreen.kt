package dev.adriankuta.partymania.feature.game.questions

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme
import dev.adriankuta.partymania.core.model.Character
import dev.adriankuta.partymania.core.ui.CharacterCard
import dev.adriankuta.partymania.core.ui.ConfirmQuitGameDialog
import dev.adriankuta.partymania.core.ui.Counter
import dev.adriankuta.partymania.core.ui.GameEndedDialog
import dev.adriankuta.partymania.core.ui.NextPlayerPrompt
import dev.adriankuta.partymania.core.ui.OverlayClickableText
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun YesOrNoScreen(
    modifier: Modifier = Modifier,
    viewModel: YesOrNoViewModel = hiltViewModel(),
    onGameEnd: () -> Unit
) {
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
            points = uiState.scoredPoints,
            currentCharacterIndex = uiState.currentCharacterIndex,
            characters = uiState.characters,
            questionsLeft = uiState.questionsLeft ?: 0,
            onCountChange = viewModel::onAnswersChange,
            onNextQuestion = viewModel::onNextCharacter
        )
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
    var showHint by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5.seconds.inWholeMilliseconds)
            showHint = !showHint
        }
    }

    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        CharacterCard(
            character = characters[currentCharacterIndex],
            currentCharacterIndex = currentCharacterIndex,
        )
        OverlayClickableText(
            text = stringResource(R.string.feature_game_questions_next_character),
            isVisible = showHint,
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .fillMaxWidth(0.5f)
                .align(Alignment.BottomEnd)
                .clickable { onNextQuestion() },
            transitionTime = 3.seconds
        )
        Counter(
            value = questionsLeft,
            label = stringResource(R.string.feature_game_questions_questions_left),
            onCountChange = onCountChange,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.25f)
                .padding(16.dp)
        )
    }
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
