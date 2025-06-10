package dev.adriankuta.partymania.ui.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.adriankuta.partymania.core.designsystem.theme.contrastiveTo

@Composable
internal fun GameButton(
    text: String,
    containerColor: Color,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contrastiveTo(containerColor),
        ),
        shape = RoundedCornerShape(25),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
        )
    }
}

@Preview
@Composable
private fun GameButtonPreview() {
    GameButton(text = "\uD83D\uDE4A Truth", containerColor = Color.Blue)
}
