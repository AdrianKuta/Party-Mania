package dev.adriankuta.partymania.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun OverlayClickableText(
    text: String,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    transitionTime: Duration = 250.milliseconds
) {
    val contentColor = MaterialTheme.colorScheme.primary
    AnimatedContent(
        isVisible,
        modifier = modifier,
        transitionSpec = {
            fadeIn(tween(transitionTime.inWholeMilliseconds.toInt())) togetherWith
                    fadeOut(tween(transitionTime.inWholeMilliseconds.toInt()))
        },
        label = "$text overlay visibility"
    ) { targetVisibility ->
        Box(
            modifier = Modifier
                .alpha(if (targetVisibility) 0.25f else 0.1f)
                .fillMaxSize()
                .border(4.dp, contentColor)
        ) {
            Text(
                text,
                modifier = Modifier.align(Alignment.Center),
                color = contentColor,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 256, heightDp = 256)
@Composable
private fun OverlayClickableTextPreview() {
    PartyManiaTheme {
        OverlayClickableText("John Doe")
    }
}