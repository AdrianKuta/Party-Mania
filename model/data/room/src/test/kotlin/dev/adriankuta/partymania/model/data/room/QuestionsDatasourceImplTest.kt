package dev.adriankuta.partymania.model.data.room

import android.content.Context
import dev.adriankuta.partymania.model.data.room.dao.ChallengesDao
import dev.adriankuta.partymania.model.data.room.dao.QuestionsDao
import dev.adriankuta.partymania.model.data.room.entities.ChallengeEntity
import dev.adriankuta.partymania.model.data.room.entities.QuestionEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class QuestionsDatasourceImplTest {

    private lateinit var questionsDao: QuestionsDao
    private lateinit var challengesDao: ChallengesDao
    private lateinit var context: Context
    private lateinit var questionsDatasource: QuestionsDatasourceImpl

    @Before
    fun setUp() {
        questionsDao = mockk(relaxed = true)
        challengesDao = mockk(relaxed = true)
        context = mockk(relaxed = true)

        questionsDatasource = QuestionsDatasourceImpl(
            questionsDao = questionsDao,
            challengesDao = challengesDao,
            context = context,
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getRandomQuestions returns unseen questions when enough are available`() = runTest {
        // Given
        val unseenQuestions = listOf(
            QuestionEntity(id = 1, values = mapOf("en" to "Question 1"), wasSeen = false),
            QuestionEntity(id = 2, values = mapOf("en" to "Question 2"), wasSeen = false),
            QuestionEntity(id = 3, values = mapOf("en" to "Question 3"), wasSeen = false),
        )

        coEvery { questionsDao.getRandomUnseenQuestions(3) } returns unseenQuestions

        // When
        val result = questionsDatasource.getRandomQuestions(3)

        // Then
        assertEquals(3, result.size)
        assertEquals(1, result[0].id)
        assertEquals(2, result[1].id)
        assertEquals(3, result[2].id)

        coVerify { questionsDao.markAsSeen(listOf(1, 2, 3)) }
        coVerify(exactly = 0) { questionsDao.getRandomQuestions(any()) }
    }

    @Test
    fun `getRandomQuestions returns mix of unseen and seen questions when not enough unseen are available`() =
        runTest {
            // Given
            val unseenQuestions = listOf(
                QuestionEntity(id = 1, values = mapOf("en" to "Question 1"), wasSeen = false),
            )

            val seenQuestions = listOf(
                QuestionEntity(id = 2, values = mapOf("en" to "Question 2"), wasSeen = true),
                QuestionEntity(id = 3, values = mapOf("en" to "Question 3"), wasSeen = true),
            )

            coEvery { questionsDao.getRandomUnseenQuestions(3) } returns unseenQuestions
            coEvery { questionsDao.getRandomQuestions(2) } returns seenQuestions

            // When
            val result = questionsDatasource.getRandomQuestions(3)

            // Then
            assertEquals(3, result.size)
            assertEquals(1, result[0].id)
            assertEquals(2, result[1].id)
            assertEquals(3, result[2].id)

            coVerify { questionsDao.markAsSeen(listOf(1, 2, 3)) }
        }

    @Test
    fun `getRandomChallenges returns unseen challenges when enough are available`() = runTest {
        // Given
        val unseenChallenges = listOf(
            ChallengeEntity(id = 1, values = mapOf("en" to "Challenge 1"), wasSeen = false),
            ChallengeEntity(id = 2, values = mapOf("en" to "Challenge 2"), wasSeen = false),
            ChallengeEntity(id = 3, values = mapOf("en" to "Challenge 3"), wasSeen = false),
        )

        coEvery { challengesDao.getRandomUnseenChallenges(3) } returns unseenChallenges

        // When
        val result = questionsDatasource.getRandomChallenges(3)

        // Then
        assertEquals(3, result.size)
        assertEquals(1, result[0].id)
        assertEquals(2, result[1].id)
        assertEquals(3, result[2].id)

        coVerify { challengesDao.markAsSeen(listOf(1, 2, 3)) }
        coVerify(exactly = 0) { challengesDao.getRandomChallenges(any()) }
    }

    @Test
    fun `getRandomChallenges returns mix of unseen and seen challenges when not enough unseen are available`() =
        runTest {
            // Given
            val unseenChallenges = listOf(
                ChallengeEntity(id = 1, values = mapOf("en" to "Challenge 1"), wasSeen = false),
            )

            val seenChallenges = listOf(
                ChallengeEntity(id = 2, values = mapOf("en" to "Challenge 2"), wasSeen = true),
                ChallengeEntity(id = 3, values = mapOf("en" to "Challenge 3"), wasSeen = true),
            )

            coEvery { challengesDao.getRandomUnseenChallenges(3) } returns unseenChallenges
            coEvery { challengesDao.getRandomChallenges(2) } returns seenChallenges

            // When
            val result = questionsDatasource.getRandomChallenges(3)

            // Then
            assertEquals(3, result.size)
            assertEquals(1, result[0].id)
            assertEquals(2, result[1].id)
            assertEquals(3, result[2].id)

            coVerify { challengesDao.markAsSeen(listOf(1, 2, 3)) }
        }

    @Test
    fun `populateInitialData populates database when empty`() = runTest {
        // Given
        val questionEntities = listOf(
            QuestionEntity(id = 1, values = mapOf("en" to "Question 1"), wasSeen = false),
            QuestionEntity(id = 2, values = mapOf("en" to "Question 2"), wasSeen = false),
        )

        val challengeEntities = listOf(
            ChallengeEntity(id = 1, values = mapOf("en" to "Challenge 1"), wasSeen = false),
            ChallengeEntity(id = 2, values = mapOf("en" to "Challenge 2"), wasSeen = false),
        )

        coEvery { questionsDao.getCount() } returns 0
        coEvery { challengesDao.getCount() } returns 0

        // Create a spy on the datasource to mock the private methods
        val spyDatasource = spyk(questionsDatasource)

        // Mock the private methods
        every { spyDatasource["getInitialQuestions"]() } returns questionEntities
        every { spyDatasource["getInitialChallenges"]() } returns challengeEntities

        // When
        spyDatasource.populateInitialData()

        // Then
        coVerify { questionsDao.insertAll(questionEntities) }
        coVerify { challengesDao.insertAll(challengeEntities) }
    }

    @Test
    fun `populateInitialData does not populate database when not empty`() = runTest {
        // Given
        coEvery { questionsDao.getCount() } returns 10
        coEvery { challengesDao.getCount() } returns 10

        // When
        questionsDatasource.populateInitialData()

        // Then
        coVerify(exactly = 0) { questionsDao.insertAll(any()) }
        coVerify(exactly = 0) { challengesDao.insertAll(any()) }
    }
}
