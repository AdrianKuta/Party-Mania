package dev.adriankuta.partymania.feature.game.questions

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.adriankuta.partymania.core.ui.theme.Elevation
import dev.adriankuta.partymania.core.ui.theme.PartyManiaTheme

@Composable
fun QuestionCountControllers(
    modifier: Modifier = Modifier,
    questionsLeft: Int,
    onCountChange: (diff: Int) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = "Licznik zadanych pytań",
            style = MaterialTheme.typography.labelLarge
        )
        Surface(
            modifier = Modifier.padding(top = 4.dp),
            tonalElevation = Elevation.Surface,
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                QuestionCount(questionsLeft = questionsLeft)
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onCountChange(1) },
                        modifier = Modifier
                            .weight(1f)
                            .height(128.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = R.string.undo),
                                style = MaterialTheme.typography.displaySmall
                            )
                            Text(
                                modifier = Modifier.padding(top = 4.dp),
                                text = stringResource(R.string.label_undo_question_used),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                    Button(
                        onClick = { onCountChange(-1) },
                        modifier = Modifier
                            .weight(1f)
                            .height(128.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = (-1).toString(),
                                style = MaterialTheme.typography.displayMedium,
                            )
                            Text(
                                modifier = Modifier.padding(top = 4.dp),
                                text = stringResource(R.string.label_question_used),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewQuestionCount(
    questionsLeft: Int,
    onCountChange: (diff: Int) -> Unit
) {

    val view = LocalView.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        AnimatedContent(
            modifier = Modifier,
            targetState = questionsLeft,
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
                enabled = questionsLeft > 0
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
                enabled = questionsLeft < 20
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
fun NewQuestionCountPreview() {
    var tapCounter by remember {
        mutableIntStateOf(20)
    }
    PartyManiaTheme {
        NewQuestionCount(
            questionsLeft = tapCounter,
            onCountChange = { tapCounter += it }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF)
@Composable
private fun QuestionCountControllersPreview() {
    PartyManiaTheme {
        QuestionCountControllers(questionsLeft = 15, onCountChange = {})
    }
}
