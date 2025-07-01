package dev.adriankuta.partymania.ui.truthordare

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme
import dev.adriankuta.partymania.core.designsystem.theme.PreviewDevices
import dev.adriankuta.partymania.ui.sharedui.R
import dev.adriankuta.partymania.ui.truthordare.components.AnimatedCurvedTopBar
import dev.adriankuta.partymania.ui.truthordare.components.ProgressIndicator
import dev.adriankuta.partymania.ui.truthordare.components.QuestionCard
import dev.adriankuta.partymania.ui.truthordare.navigation.GameVariant

@Composable
fun TruthOrDareScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TruthOrDareViewModel = hiltViewModel(),
) {
    val gameUiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (gameUiState.isGameFinished) {
        AlertDialog(
            title = {
                Text(
                    text = stringResource(R.string.game_finished),
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.game_finished_message),
                )
            },
            onDismissRequest = onFinish,
            confirmButton = {
                TextButton(onClick = onFinish) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            },
        )
    }

    TruthOrDareScreen(
        gameUiState = gameUiState,
        onNextClick = viewModel::onNextClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TruthOrDareScreen(
    gameUiState: TruthOrDareUiState,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        GameContent(
            gameUiState = gameUiState,
            onNextClick = onNextClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = TopAppBarDefaults.LargeAppBarExpandedHeight,
                ),
        )

        AnimatedCurvedTopBar {
            Text(
                text = stringResource(gameNameRes(gameUiState.variant)),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@StringRes
private fun gameNameRes(gameType: GameVariant): Int = when (gameType) {
    GameVariant.Truth -> R.string.game_type_truth
    GameVariant.Random -> R.string.game_type_random
    GameVariant.Challenge -> R.string.game_type_challenge
}

@Composable
private fun GameContent(
    gameUiState: TruthOrDareUiState,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentQuestion = gameUiState.questions.getOrNull(gameUiState.currentQuestionIndex)
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (gameUiState.questions.isNotEmpty()) {
            ProgressIndicator(
                progress = (gameUiState.currentQuestionIndex + 1) / gameUiState.questions.size.toFloat(),
                modifier = Modifier.fillMaxWidth(0.85f),
            )
            Text(
                text = "${gameUiState.currentQuestionIndex + 1} / ${gameUiState.questions.size}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }

        // Card with animated content
        QuestionCard(
            text = currentQuestion ?: "",
            modifier = Modifier
                .aspectRatio(4 / 3f),
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Next button with enhanced styling
        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.VirtualKey)
                onNextClick()
            },
            modifier = Modifier
                .fillMaxWidth(0.7f),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(
                text = stringResource(R.string.next),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}

@PreviewDevices
@Composable
private fun TruthOrDareScreenPreview() {
    PartyManiaTheme {
        TruthOrDareScreen(
            gameUiState = TruthOrDareUiState(
                variant = GameVariant.Truth,
                questions = listOf(
                    "What is your most embarrassing childhood memory?",
                    "If you could be invisible for a day, what would you do?",
                    "What's the most awkward date you've ever been on?",
                    "What's one thing you'd change about yourself if you could?",
                    "What's the most rebellious thing you've ever done?",
                ),
                currentQuestionIndex = 2,
            ),
            onNextClick = {},
        )
    }
}

@PreviewDevices
@Composable
private fun TruthOrDareScreenEmptyPreview() {
    PartyManiaTheme {
        TruthOrDareScreen(
            gameUiState = TruthOrDareUiState(
                variant = GameVariant.Challenge,
                questions = emptyList(),
                currentQuestionIndex = -1,
            ),
            onNextClick = {},
        )
    }
}
