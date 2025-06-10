package dev.adriankuta.partymania.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    internal val uiState = UiState(
        games = GameType.entries,
    )
}
