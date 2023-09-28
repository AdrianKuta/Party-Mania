package dev.adriankuta.partymania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.adriankuta.partymania.core.ui.theme.PartyManiaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PartyManiaTheme {
                PartyManiaNavGraph()
            }
        }
    }
}