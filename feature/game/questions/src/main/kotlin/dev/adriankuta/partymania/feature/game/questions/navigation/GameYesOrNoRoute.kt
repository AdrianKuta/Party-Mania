package dev.adriankuta.partymania.feature.game.questions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.adriankuta.partymania.feature.game.questions.YesOrNoScreen
import kotlinx.serialization.Serializable

@Serializable
data object GameYesOrNoRoute

fun NavController.navigateToGameYesOrNo(navOptions: NavOptions) =
    navigate(GameYesOrNoRoute, navOptions)

fun NavGraphBuilder.questionsGameScreen(
    onGameEnd: () -> Unit,
) {
    composable<GameYesOrNoRoute> {
        YesOrNoScreen(
            onGameEnd = onGameEnd,
        )
    }
}
