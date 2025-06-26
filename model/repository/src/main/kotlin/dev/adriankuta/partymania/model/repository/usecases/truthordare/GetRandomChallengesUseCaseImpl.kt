package dev.adriankuta.partymania.model.repository.usecases.truthordare

import dev.adriankuta.partymania.domain.truthordare.GetRandomChallengesUseCase
import dev.adriankuta.partymania.domain.truthordare.entity.Challenge
import dev.adriankuta.partymania.model.datasource.questions.QuestionsDatasource
import dev.adriankuta.partymania.model.repository.mappers.challenges.toDomain
import javax.inject.Inject

internal class GetRandomChallengesUseCaseImpl @Inject constructor(
    private val questionsDatasource: QuestionsDatasource,
) : GetRandomChallengesUseCase {

    override suspend fun invoke(count: Int): List<Challenge> {
        val challenges = questionsDatasource.getRandomChallenges(count)
        return challenges.map { it.toDomain() }
    }
}
