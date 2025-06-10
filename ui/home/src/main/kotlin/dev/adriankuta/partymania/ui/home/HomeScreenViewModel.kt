package dev.adriankuta.partymania.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.adriankuta.partymania.domain.gametypes.GetGameTypesUseCase
import dev.adriankuta.partymania.domain.gametypes.entities.GameType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal data class HomeUiState(
    val games: ImmutableList<GameType> = persistentListOf(),
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    getGameTypesUseCase: GetGameTypesUseCase,
) : ViewModel() {

    internal val uiState: StateFlow<HomeUiState> = homeUiState(
        useCase = getGameTypesUseCase,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState(),
    )
}

private fun homeUiState(
    useCase: GetGameTypesUseCase,
): Flow<HomeUiState> = flow {
    emit(HomeUiState(games = useCase().toImmutableList()))
}
