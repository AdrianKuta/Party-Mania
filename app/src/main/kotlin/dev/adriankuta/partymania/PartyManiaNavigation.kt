package dev.adriankuta.partymania

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import dev.adriankuta.partymania.ui.gametype.GameType

private object PartyManiaScreens {
    const val SPLASH_SCREEN = "splashScreen"
    const val HOME_SCREEN = "home"
    const val YES_OR_NO = "yesOrNo"
}

object PartyManiaDestinations {
    const val SPLASH_ROUTE = PartyManiaScreens.SPLASH_SCREEN
    const val HOME_ROUTE = PartyManiaScreens.HOME_SCREEN
    const val GAME_YES_OR_NO_ROUTE = PartyManiaScreens.YES_OR_NO
}

class PartyManiaNavigationActions(private val navController: NavHostController) {

    fun navigateToSplash() {
        navController.navigate(PartyManiaDestinations.SPLASH_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(PartyManiaDestinations.SPLASH_ROUTE) { inclusive = true }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
        }
    }

    fun navigateToHome() {
        navController.navigate(PartyManiaDestinations.HOME_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(PartyManiaDestinations.SPLASH_ROUTE) {
                inclusive = true
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToGame(gameType: GameType) {
        val route = when (gameType) {
            GameType.YES_OR_NO -> PartyManiaDestinations.GAME_YES_OR_NO_ROUTE
            else -> PartyManiaDestinations.SPLASH_ROUTE
        }
        navController.navigate(route) {
            launchSingleTop = true
        }
    }
}