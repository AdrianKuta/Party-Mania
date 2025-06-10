package dev.adriankuta.partymania.ui.truthordare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.adriankuta.partymania.ui.truthordare.navigation.GameVariant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = TruthOrDareViewModel.Factory::class)
class TruthOrDareViewModel @AssistedInject constructor(
    @Assisted private val gameVariant: GameVariant,
) : ViewModel() {

    val variant = gameUiState(gameVariant)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TruthOrDareUiState(gameVariant),
        )

    @AssistedFactory
    interface Factory {
        fun create(gameVariant: GameVariant): TruthOrDareViewModel
    }
}

private fun gameUiState(variant: GameVariant): Flow<TruthOrDareUiState> = flow {
    emit(TruthOrDareUiState(variant))
}

data class TruthOrDareUiState(
    val variant: GameVariant,
)
