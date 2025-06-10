@file:Suppress("MatchingDeclarationName")

package dev.adriankuta.partymania.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.adriankuta.partymania.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen() {
    composable<HomeRoute> {
        HomeScreen()
    }
}
