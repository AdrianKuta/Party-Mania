package dev.adriankuta.partymania.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.adriankuta.partymania.PartyManiaNavGraph
import dev.adriankuta.partymania.core.ui.theme.Elevation
import dev.adriankuta.partymania.util.InAppUpdates

@Composable
fun PartyManiaApp(
    modifier: Modifier = Modifier,
) {
    Surface(
        tonalElevation = Elevation.Surface
    ) {
        Scaffold(
            modifier = modifier,
            snackbarHost = { InAppUpdates() },
        ) { paddingValues ->
            PartyManiaNavGraph(modifier.padding(paddingValues))

        }
    }
}