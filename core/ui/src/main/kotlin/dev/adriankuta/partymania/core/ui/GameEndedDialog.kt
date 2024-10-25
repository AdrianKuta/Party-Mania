package dev.adriankuta.partymania.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun GameEndedDialog(
    points: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
        title = {
            Text(text = stringResource(R.string.core_ui_congratulations))
        },
        text = {
            Text(text = stringResource(R.string.core_ui_scored_points, points))
        }
    )
}