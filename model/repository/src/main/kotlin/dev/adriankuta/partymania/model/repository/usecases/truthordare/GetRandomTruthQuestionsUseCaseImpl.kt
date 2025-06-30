package dev.adriankuta.partymania.model.repository.usecases.truthordare

import dev.adriankuta.partymania.domain.truthordare.GetRandomTruthQuestionsUseCase
import dev.adriankuta.partymania.domain.truthordare.entity.TruthQuestion
import dev.adriankuta.partymania.model.datasource.questions.QuestionsDatasource
import dev.adriankuta.partymania.model.repository.mappers.questions.toDomain
import javax.inject.Inject

internal class GetRandomTruthQuestionsUseCaseImpl @Inject constructor(
    private val questionsDatasource: QuestionsDatasource,
) : GetRandomTruthQuestionsUseCase {

    override suspend fun invoke(count: Int): List<TruthQuestion> {
        val questions = questionsDatasource.getRandomQuestions(count)
        return questions.map { it.toDomain() }
    }
}
