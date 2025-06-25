package dev.adriankuta.partymania.model.datasource.questions

import dev.adriankuta.partymania.model.datasource.questions.entities.ChallengeModel
import dev.adriankuta.partymania.model.datasource.questions.entities.QuestionModel

interface QuestionsDatasource {
    suspend fun getRandomQuestions(count: Int): List<QuestionModel>
    suspend fun getRandomChallenges(count: Int): List<ChallengeModel>
}
