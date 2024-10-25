package dev.adriankuta.partymania.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties

@Composable
fun NextPlayerPrompt(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.core_ui_done))
            }
        },
        title = {
            Text(text = stringResource(R.string.core_ui_next_turn))
        },
        text = {
            Text(text = stringResource(R.string.core_ui_next_turn_prompt))
        }
    )
}