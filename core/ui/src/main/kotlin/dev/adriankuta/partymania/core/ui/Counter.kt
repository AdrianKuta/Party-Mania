package dev.adriankuta.partymania.core.ui

import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.adriankuta.partymania.core.designsystem.theme.PartyManiaTheme

@Composable
fun Counter(
    value: Int,
    onCountChange: (diff: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val view = LocalView.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.width(IntrinsicSize.Min)
    ) {
        AnimatedContent(
            modifier = Modifier,
            targetState = value,
            label = "Questions counter animation",
            transitionSpec = {
                if (targetState > initialState) {
                    scaleIn() + slideInHorizontally { it / 2 } togetherWith
                            scaleOut() + slideOutHorizontally { -it / 2 } using
                            SizeTransform(clip = false)
                } else {
                    scaleIn() + slideInHorizontally { -it / 2 } togetherWith
                            scaleOut() + slideOutHorizontally { it / 2 } using
                            SizeTransform(clip = false)
                }
            }
        ) { state ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "$state",
                style = MaterialTheme.typography.displayMedium,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    onCountChange(-1)
                },
                shape = MaterialTheme.shapes.medium,
                enabled = value > 0
            ) {
                Text(
                    text = "-",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    onCountChange(1)
                },
                shape = MaterialTheme.shapes.medium,
                enabled = value < 20
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPreview() {
    var tapCounter by remember {
        mutableIntStateOf(20)
    }
    PartyManiaTheme {
        Counter(
            value = tapCounter,
            onCountChange = { tapCounter += it }
        )
    }
}