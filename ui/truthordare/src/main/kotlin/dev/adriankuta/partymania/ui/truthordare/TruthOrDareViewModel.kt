package dev.adriankuta.partymania.ui.truthordare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.adriankuta.partymania.domain.truthordare.GetRandomChallengesUseCase
import dev.adriankuta.partymania.domain.truthordare.GetRandomTruthQuestionsUseCase
import dev.adriankuta.partymania.ui.truthordare.navigation.GameVariant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.math.min
import kotlin.random.Random

private const val QuestionsCount = 10

@HiltViewModel(assistedFactory = TruthOrDareViewModel.Factory::class)
class TruthOrDareViewModel @AssistedInject constructor(
    @Assisted private val gameVariant: GameVariant,
    getRandomTruthQuestionsUseCase: GetRandomTruthQuestionsUseCase,
    getRandomChallengesUseCase: GetRandomChallengesUseCase,
) : ViewModel() {

    private val currentQuestionIndex = MutableStateFlow(0)

    val variant = gameUiState(
        variant = gameVariant,
        getRandomQuestions = getRandomTruthQuestionsUseCase,
        getRandomChallenges = getRandomChallengesUseCase,
        currentQuestionIndex = currentQuestionIndex,
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TruthOrDareUiState(gameVariant),
        )

    fun onNextClick() {
        currentQuestionIndex.value = currentQuestionIndex.value + 1
    }

    @AssistedFactory
    interface Factory {
        fun create(gameVariant: GameVariant): TruthOrDareViewModel
    }
}

private fun gameUiState(
    variant: GameVariant,
    getRandomQuestions: GetRandomTruthQuestionsUseCase,
    getRandomChallenges: GetRandomChallengesUseCase,
    currentQuestionIndex: StateFlow<Int>,
): Flow<TruthOrDareUiState> = combine(
    prepareGameContent(getRandomQuestions, getRandomChallenges, variant),
    currentQuestionIndex,
) { questions, index ->
    TruthOrDareUiState(
        variant = variant,
        questions = questions,
        currentQuestionIndex = min(index, questions.lastIndex),
        isGameFinished = index > questions.lastIndex,
    )
}

private fun prepareGameContent(
    getRandomQuestions: GetRandomTruthQuestionsUseCase,
    getRandomChallenges: GetRandomChallengesUseCase,
    variant: GameVariant,
): Flow<List<String>> = flow {
    val content = when (variant) {
        GameVariant.Truth -> getRandomQuestions(QuestionsCount).map { it.value }
        GameVariant.Challenge -> getRandomChallenges(QuestionsCount).map { it.value }
        GameVariant.Random -> prepareRandomContent(
            getRandomQuestions = getRandomQuestions,
            getRandomChallenges = getRandomChallenges,
            count = QuestionsCount,
        )
    }
    emit(content)
}

private suspend fun prepareRandomContent(
    getRandomQuestions: GetRandomTruthQuestionsUseCase,
    getRandomChallenges: GetRandomChallengesUseCase,
    count: Int,
): List<String> {
    val questionsCount = Random.nextInt(1, count + 1)
    val challengesCount = count - questionsCount
    val questions = getRandomQuestions(questionsCount)
    val challenges = getRandomChallenges(challengesCount)
    return questions.map { it.value } + challenges.map { it.value }
}

data class TruthOrDareUiState(
    val variant: GameVariant,
    val questions: List<String> = emptyList(),
    val currentQuestionIndex: Int = -1,
    val isGameFinished: Boolean = false,
)
