package dev.adriankuta.partymania

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.adriankuta.partymania.feature.game.questions.YesOrNoScreen
import dev.adriankuta.partymania.screens.home.HomeScreen
import dev.adriankuta.partymania.screens.splash.SplashScreen

@Composable
fun PartyManiaNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = PartyManiaDestinations.SPLASH_ROUTE,
    navActions: PartyManiaNavigationActions = remember(navController) {
        PartyManiaNavigationActions(navController)
    }
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(PartyManiaDestinations.SPLASH_ROUTE) {
            SplashScreen(navActions)
        }
        composable(PartyManiaDestinations.HOME_ROUTE) {
            HomeScreen(
                onStartGame = navActions::navigateToGame
            )
        }
        composable(PartyManiaDestinations.GAME_YES_OR_NO_ROUTE) {
            YesOrNoScreen(
                onGameEnd = { navController.navigateUp() }
            )
        }
    }
}