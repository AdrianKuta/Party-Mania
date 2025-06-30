package dev.adriankuta.partymania.model.data.room

import android.content.Context
import com.google.gson.reflect.TypeToken
import dev.adriankuta.partymania.model.data.room.dao.ChallengesDao
import dev.adriankuta.partymania.model.data.room.dao.QuestionsDao
import dev.adriankuta.partymania.model.data.room.dto.ChallengeDto
import dev.adriankuta.partymania.model.data.room.dto.QuestionDto
import dev.adriankuta.partymania.model.data.room.entities.ChallengeEntity
import dev.adriankuta.partymania.model.data.room.entities.QuestionEntity
import dev.adriankuta.partymania.model.data.room.mappers.toEntity
import dev.adriankuta.partymania.model.data.room.mappers.toModel
import dev.adriankuta.partymania.model.data.room.utils.JsonUtils
import dev.adriankuta.partymania.model.datasource.questions.QuestionsDatasource
import dev.adriankuta.partymania.model.datasource.questions.entities.ChallengeModel
import dev.adriankuta.partymania.model.datasource.questions.entities.QuestionModel
import javax.inject.Inject

internal class QuestionsDatasourceImpl @Inject constructor(
    private val questionsDao: QuestionsDao,
    private val challengesDao: ChallengesDao,
    private val context: Context,
) : QuestionsDatasource {

    override suspend fun getRandomQuestions(count: Int): List<QuestionModel> {
        // Try to get unseen questions first
        var questions = questionsDao.getRandomUnseenQuestions(count)

        // If we don't have enough unseen questions, get random ones
        if (questions.size < count) {
            val remainingCount = count - questions.size
            questions = questions + questionsDao.getRandomQuestions(remainingCount)
        }

        // Mark the questions as seen
        val ids = questions.map { it.id }
        if (ids.isNotEmpty()) {
            questionsDao.markAsSeen(ids)
        }

        return questions.map { it.toModel() }
    }

    override suspend fun getRandomChallenges(count: Int): List<ChallengeModel> {
        // Try to get unseen challenges first
        var challenges = challengesDao.getRandomUnseenChallenges(count)

        // If we don't have enough unseen challenges, get random ones
        if (challenges.size < count) {
            val remainingCount = count - challenges.size
            challenges = challenges + challengesDao.getRandomChallenges(remainingCount)
        }

        // Mark the challenges as seen
        val ids = challenges.map { it.id }
        if (ids.isNotEmpty()) {
            challengesDao.markAsSeen(ids)
        }

        return challenges.map { it.toModel() }
    }

    suspend fun populateInitialData() {
        // Only populate if the database is empty
        if (questionsDao.getCount() == 0) {
            val initialQuestions = getInitialQuestions()
            questionsDao.insertAll(initialQuestions)
        }

        if (challengesDao.getCount() == 0) {
            val initialChallenges = getInitialChallenges()
            challengesDao.insertAll(initialChallenges)
        }
    }

    private fun getInitialQuestions(): List<QuestionEntity> {
        return JsonUtils.parseJsonFromAssets(
            context = context,
            fileName = "questions.json",
            typeToken = object : TypeToken<List<QuestionDto>>() {},
            mapper = { it.toEntity() },
        )
    }

    private fun getInitialChallenges(): List<ChallengeEntity> {
        return JsonUtils.parseJsonFromAssets(
            context = context,
            fileName = "challenges.json",
            typeToken = object : TypeToken<List<ChallengeDto>>() {},
            mapper = { it.toEntity() },
        )
    }
}
