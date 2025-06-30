package dev.adriankuta.partymania.ui.truthordare.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme

@Composable
internal fun ProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val animatedProgress: Float by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "ProgressAnimation",
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun ProgressIndicatorPreview() {
    var progress by remember { mutableFloatStateOf(0.1f) }
    LaunchedEffect(Unit) {
        while (progress < 1f) {
            progress += 0.1f
            kotlinx.coroutines.delay(1000)
        }
    }
    PartyManiaTheme {
        ProgressIndicator(
            progress = progress,
            modifier = Modifier,
        )
    }
}
