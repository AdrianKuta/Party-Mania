package dev.adriankuta.partymania.feature.game.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.adriankuta.partymania.domain.types.Character
import dev.adriankuta.partymania.domain.yesorno.GetRandomCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class YesNoGameUiState(
    val questionsLeft: Int? = null,
    val currentCharacterIndex: Int = 0,
    val characters: List<Character> = emptyList(),
    val scoredPoints: Int = 0,
    val isGameEnd: Boolean = false,
    val showNextPlayerPrompt: Boolean = false,
    val isQuitByUser: Boolean = false,
)

const val CharactersCount = 20

@HiltViewModel
class YesOrNoViewModel @Inject constructor(
    private val getRandomCharactersUseCase: GetRandomCharactersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(YesNoGameUiState())
    val uiState: StateFlow<YesNoGameUiState> = _uiState.asStateFlow()

    init {
        loadGame()
    }

    private fun loadGame() {
        viewModelScope.launch {
            val randomCharacters = getRandomCharactersUseCase(CharactersCount)
            _uiState.value = YesNoGameUiState(
                questionsLeft = CharactersCount,
                characters = randomCharacters,
                currentCharacterIndex = 0,
            )
        }
    }

    fun onAnswersChange(diff: Int) {
        _uiState.update {
            val newQuestionsCount = (it.questionsLeft ?: 0) + diff
            it.copy(
                questionsLeft = newQuestionsCount.coerceIn(0, CharactersCount),
            )
        }
    }

    fun onNextCharacter() {
        _uiState.update {
            val isGameEnd = it.currentCharacterIndex + 1 >= it.characters.size
            if (isGameEnd || it.showNextPlayerPrompt) {
                val newIndex =
                    if (isGameEnd) it.currentCharacterIndex else it.currentCharacterIndex + 1
                it.copy(
                    scoredPoints = it.scoredPoints + (it.questionsLeft ?: 0),
                    questionsLeft = 20,
                    isGameEnd = isGameEnd,
                    currentCharacterIndex = newIndex,
                    showNextPlayerPrompt = false,
                )
            } else {
                it.copy(
                    showNextPlayerPrompt = true,
                )
            }
        }
    }

    fun onQuitGame() {
        _uiState.update {
            it.copy(
                isQuitByUser = true,
            )
        }
    }

    fun confirmQuitGame() {
        _uiState.update {
            it.copy(
                isQuitByUser = false,
                isGameEnd = true,
            )
        }
    }

    fun cancelQuitGame() {
        _uiState.update {
            it.copy(
                isQuitByUser = false,
            )
        }
    }
}
