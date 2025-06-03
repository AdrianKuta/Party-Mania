package dev.adriankuta.partymania

import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import dev.adriankuta.partymania.feature.game.questions.navigation.navigateToGameYesOrNo
import dev.adriankuta.partymania.ui.gametype.GameType

private object PartyManiaScreens {
    const val HOME_SCREEN = "home"
}

object PartyManiaDestinations {
    const val HOME_ROUTE = PartyManiaScreens.HOME_SCREEN
}

class PartyManiaNavigationActions(private val navController: NavHostController) {

    fun navigateToHome() {
        navController.navigate(PartyManiaDestinations.HOME_ROUTE) {
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToGame(gameType: GameType) {
        val gameNavOptions = navOptions {
            launchSingleTop = true
        }
        when (gameType) {
            GameType.YES_OR_NO -> navController.navigateToGameYesOrNo(gameNavOptions)
            else -> Unit
        }
    }
}
