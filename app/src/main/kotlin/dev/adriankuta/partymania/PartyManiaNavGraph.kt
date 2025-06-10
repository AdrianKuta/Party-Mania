package dev.adriankuta.partymania

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.adriankuta.partymania.feature.game.questions.navigation.questionsGameScreen
import dev.adriankuta.partymania.screens.home.HomeScreen
import dev.adriankuta.partymania.ui.home.navigation.HomeRoute
import dev.adriankuta.partymania.ui.home.navigation.homeScreen

@Composable
fun PartyManiaNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navActions: PartyManiaNavigationActions = remember(navController) {
        PartyManiaNavigationActions(navController)
    },
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        homeScreen()
        composable(PartyManiaDestinations.HOME_ROUTE) {
            HomeScreen(
                onStartGame = navActions::navigateToGame,
            )
        }
        questionsGameScreen { navController.navigateUp() }
    }
}
