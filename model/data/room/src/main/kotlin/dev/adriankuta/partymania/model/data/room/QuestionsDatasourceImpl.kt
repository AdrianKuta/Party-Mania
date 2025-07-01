package dev.adriankuta.partymania.model.data.room

import android.content.Context
import com.google.gson.reflect.TypeToken
import dev.adriankuta.partymania.model.data.room.dao.ChallengesDao
import dev.adriankuta.partymania.model.data.room.dao.QuestionsDao
import dev.adriankuta.partymania.model.data.room.dto.ContentDto
import dev.adriankuta.partymania.model.data.room.entities.ChallengeEntity
import dev.adriankuta.partymania.model.data.room.entities.QuestionEntity
import dev.adriankuta.partymania.model.data.room.mappers.toModel
import dev.adriankuta.partymania.model.data.room.utils.JsonUtils
import dev.adriankuta.partymania.model.datasource.questions.QuestionsDatasource
import dev.adriankuta.partymania.model.datasource.questions.entities.ChallengeModel
import dev.adriankuta.partymania.model.datasource.questions.entities.QuestionModel
import java.util.Locale
import javax.inject.Inject

internal class QuestionsDatasourceImpl @Inject constructor(
    private val questionsDao: QuestionsDao,
    private val challengesDao: ChallengesDao,
    private val context: Context,
) : QuestionsDatasource {
    private val languageTag = context.resources.getString(R.string.content_language_tag)
    private val locale =
        if (languageTag.isEmpty()) {
            Locale.ENGLISH
        } else {
            Locale.forLanguageTag(languageTag)
        }

    override suspend fun getRandomQuestions(count: Int): List<QuestionModel> {
        // Try to get unseen questions first
        var questions = questionsDao.getRandomUnseenQuestions(count, locale)

        // If we don't have enough unseen questions, get random ones
        if (questions.size < count) {
            val remainingCount = count - questions.size
            questions = questions + questionsDao.getRandomQuestions(remainingCount, locale)
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
        var challenges = challengesDao.getRandomUnseenChallenges(count, locale)

        // If we don't have enough unseen challenges, get random ones
        if (challenges.size < count) {
            val remainingCount = count - challenges.size
            challenges = challenges + challengesDao.getRandomChallenges(remainingCount, locale)
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
        val content = getInitialContent() ?: return

        if (questionsDao.getCount(locale) < content.truth.size) {
            val initialQuestions = content.truth.map {
                QuestionEntity(
                    locale = locale,
                    text = it,
                    wasSeen = false,
                )
            }
            questionsDao.insertAll(initialQuestions)
        }

        if (challengesDao.getCount(locale) < content.dare.size) {
            val initialChallenges = content.dare.map {
                ChallengeEntity(
                    locale = locale,
                    text = it,
                    wasSeen = false,
                )
            }
            challengesDao.insertAll(initialChallenges)
        }
    }

    private fun getInitialContent(): ContentDto? {
        val contentFileName = context.resources.getString(R.string.content_file_name)

        return JsonUtils.parseJsonFromAssets(
            context = context,
            fileName = contentFileName,
            typeToken = object : TypeToken<ContentDto>() {},
            mapper = { it },
        )
    }
}
