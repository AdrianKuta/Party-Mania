package dev.adriankuta.partymania.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    @StringRes gameName: Int,
    closeGame: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
    ) {
        TopAppBar(
            title = { Text(text = stringResource(id = gameName)) },
            navigationIcon = {
                IconButton(onClick = closeGame) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        stringResource(id = R.string.core_ui_close_game),
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        )
    }
}
