package dev.adriankuta.partymania.model.repository.usecases.truthordare

import dev.adriankuta.partymania.domain.truthordare.GetRandomTruthQuestionsUseCase
import dev.adriankuta.partymania.domain.truthordare.entity.TruthQuestion
import dev.adriankuta.partymania.model.datasource.questions.QuestionsDatasource
import javax.inject.Inject

class GetRandomTruthQuestionsUseCaseImpl @Inject constructor(
    val questionsDatasource: QuestionsDatasource,
) : GetRandomTruthQuestionsUseCase {

    override suspend fun invoke(count: Int): List<TruthQuestion> {
        return emptyList()
    }
}
