package dev.adriankuta.partymania.ui.truthordare.navigation

import androidx.annotation.Keep
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.adriankuta.partymania.ui.truthordare.TruthOrDareScreen
import dev.adriankuta.partymania.ui.truthordare.TruthOrDareViewModel
import kotlinx.serialization.Serializable

@Keep
enum class GameVariant {
    Truth, Challenge, Random
}

@Serializable
data class TruthOrDareRoute(val variant: GameVariant)

fun NavController.navigateToTruthOrDare(
    variant: GameVariant,
    navOptions: NavOptionsBuilder.() -> Unit = {},
) {
    navigate(route = TruthOrDareRoute(variant)) {
        navOptions()
    }
}

fun NavGraphBuilder.truthOrDareScreen(
    onFinish: () -> Unit,
) {
    composable<TruthOrDareRoute> { entry ->
        val variant = entry.toRoute<TruthOrDareRoute>().variant
        TruthOrDareScreen(
            onFinish = onFinish,
            viewModel = hiltViewModel<TruthOrDareViewModel, TruthOrDareViewModel.Factory>(
                key = variant.name,
            ) { factory ->
                factory.create(variant)
            },
        )
    }
}
