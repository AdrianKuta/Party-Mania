package dev.adriankuta.partymania.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.adriankuta.partymania.PartyManiaNavigationActions
import dev.adriankuta.partymania.core.ui.theme.PartyManiaTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navActions: PartyManiaNavigationActions,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit) {
        delay(1000)
        navActions.navigateToHome()
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Greeting(
            name = "Wiktoria",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = "Witaj $name!",
            modifier = Modifier
                .align(Alignment.Center),
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PartyManiaTheme {
        Greeting("Android")
    }
}