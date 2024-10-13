package dev.adriankuta.partymania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.adriankuta.partymania.core.ui.theme.PartyManiaTheme
import dev.adriankuta.partymania.ui.PartyManiaApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var isLoading: Boolean by mutableStateOf(true)

        lifecycleScope.launch {
            delay(500)
            isLoading = false
        }

        splashScreen.setKeepOnScreenCondition {
            isLoading
        }

        enableEdgeToEdge()

        setContent {
            PartyManiaTheme {
                PartyManiaApp()
            }
        }
    }
}