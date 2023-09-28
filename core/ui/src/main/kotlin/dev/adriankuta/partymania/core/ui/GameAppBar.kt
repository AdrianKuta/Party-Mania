package dev.adriankuta.partymania.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.adriankuta.partymania.core.ui.theme.Elevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    @StringRes gameName: Int,
    closeGame: () -> Unit
) {
    Surface(
    ) {
        TopAppBar(
            title = { Text(text = stringResource(id = gameName)) },
            navigationIcon = {
                IconButton(onClick = closeGame) {
                    Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.close_game))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}