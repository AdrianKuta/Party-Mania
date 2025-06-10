package dev.adriankuta.partymania

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.adriankuta.partymania.domain.gametypes.entities.GameType
import dev.adriankuta.partymania.feature.game.questions.navigation.questionsGameScreen
import dev.adriankuta.partymania.ui.home.navigation.HomeRoute
import dev.adriankuta.partymania.ui.home.navigation.homeScreen
import dev.adriankuta.partymania.ui.truthordare.navigation.GameVariant
import dev.adriankuta.partymania.ui.truthordare.navigation.navigateToTruthOrDare
import dev.adriankuta.partymania.ui.truthordare.navigation.truthOrDareScreen

@Composable
fun PartyManiaNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        homeScreen(
            onNavigateToGame = { gameType ->
                when (gameType) {
                    GameType.Truth -> navController.navigateToTruthOrDare(GameVariant.Truth)
                    GameType.Challenge -> navController.navigateToTruthOrDare(GameVariant.Challenge)
                    GameType.Random -> navController.navigateToTruthOrDare(GameVariant.Random)
                }
            },
        )

        truthOrDareScreen()
        questionsGameScreen { navController.navigateUp() }
    }
}
