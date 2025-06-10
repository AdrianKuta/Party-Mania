package dev.adriankuta.partymania.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.adriankuta.partymania.PartyManiaNavGraph
import dev.adriankuta.partymania.core.designsystem.theme.Elevation

@Composable
fun PartyManiaApp(
    modifier: Modifier = Modifier,
) {
    Surface(
        tonalElevation = Elevation.Surface,
        modifier = modifier,
    ) {
        Scaffold(
            snackbarHost = { InAppUpdates() },
        ) { paddingValues ->
            PartyManiaNavGraph(Modifier.padding(paddingValues))
        }
    }
}
