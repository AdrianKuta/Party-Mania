package dev.adriankuta.partymania.ui.truthordare

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.adriankuta.partymania.core.designsystem.theme.PreviewDevices
import dev.adriankuta.partymania.ui.truthordare.components.AnimatedCurvedTopBar
import dev.adriankuta.partymania.ui.truthordare.navigation.GameVariant

@Composable
fun TruthOrDareScreen(
    modifier: Modifier = Modifier,
    viewModel: TruthOrDareViewModel = hiltViewModel(),
) {
    val gameUiState by viewModel.variant.collectAsStateWithLifecycle()
    TruthOrDareScreen(
        gameUiState = gameUiState,
        modifier = modifier,
    )
}

@Composable
private fun TruthOrDareScreen(
    gameUiState: TruthOrDareUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        AnimatedCurvedTopBar {
            Text(
                text = gameUiState.variant.name,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@PreviewDevices
@Composable
private fun TruthOrDareScreenPreview() {
    TruthOrDareScreen(
        gameUiState = TruthOrDareUiState(GameVariant.Truth),
    )
}
