package dev.adriankuta.partymania.screens.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.adriankuta.partymania.R
import dev.adriankuta.partymania.ui.gametype.GameType
import dev.adriankuta.partymania.ui.gametype.GameType.CHALLENGE
import dev.adriankuta.partymania.ui.gametype.GameType.IMPROVISATIONS
import dev.adriankuta.partymania.ui.gametype.GameType.RIDDLE
import dev.adriankuta.partymania.ui.gametype.GameType.YES_OR_NO
import dev.adriankuta.partymania.util.WhileUiSubscribed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class HomeUiState(
    val isLoading: Boolean = false,
    val gameTypes: List<GameTypeUIInfo> = emptyList(),
    val selectedGameType: GameTypeUIInfo? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _selectedGameType = MutableStateFlow<GameTypeUIInfo?>(null)
    private val _gameTypesRepo = flow {
        emit(listOf(CHALLENGE, RIDDLE, IMPROVISATIONS, YES_OR_NO).map { getGameTypeUiInfo(it) })
        _isLoading.value = false
    }.shareIn(
        scope = viewModelScope,
        started = WhileUiSubscribed
    ).combine(_selectedGameType) { gameTypes, selectedType ->
        gameTypes.filter { type -> selectedType == null || type.title == selectedType.title }
    }

    val uiState: StateFlow<HomeUiState> = combine(
        _isLoading, _selectedGameType, _gameTypesRepo
    ) { isLoading, gameType, gameTypes ->
        if (isLoading) {
            HomeUiState(isLoading = true)
        } else {
            HomeUiState(selectedGameType = gameType, gameTypes = gameTypes)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = HomeUiState(isLoading = true)
    )

    fun onGameTypeSelected(gameTypeUIInfo: GameTypeUIInfo) {
        _selectedGameType.value = gameTypeUIInfo
    }

    private fun getGameTypeUiInfo(gameType: GameType): GameTypeUIInfo = GameTypeUIInfo(gameType)

    fun clearSelectedType() {
        _selectedGameType.value = null
    }

}

data class GameTypeUIInfo(
    val gameType: GameType,
) {
    val title: Int
        get() = getTitleRes()

    val description: Int
        get() = getDescriptionRes()

    private fun getTitleRes() =
        when (gameType) {
            CHALLENGE -> R.string.game_type_challange
            RIDDLE ->  R.string.game_type_riddle
            IMPROVISATIONS ->  R.string.game_type_improvisation
            YES_OR_NO -> R.string.game_type_yes_or_no
        }

    private fun getDescriptionRes() =
        when (gameType) {
            CHALLENGE -> R.string.game_type_challange_description
            RIDDLE ->  R.string.game_type_riddle_description
            IMPROVISATIONS ->  R.string.game_type_improvisation_description
            YES_OR_NO -> R.string.game_type_yes_or_no_description
        }
}