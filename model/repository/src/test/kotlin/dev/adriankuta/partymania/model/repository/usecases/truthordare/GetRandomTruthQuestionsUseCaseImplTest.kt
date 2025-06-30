package dev.adriankuta.partymania.model.repository.usecases.truthordare

import dev.adriankuta.partymania.model.datasource.questions.QuestionsDatasource
import dev.adriankuta.partymania.model.datasource.questions.entities.QuestionModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRandomTruthQuestionsUseCaseImplTest {

    @Test
    fun `invoke returns mapped questions from datasource`() = runTest {
        // Given
        val testQuestions = listOf(
            QuestionModel(id = 1, text = "Question 1", wasSeen = false),
            QuestionModel(id = 2, text = "Question 2", wasSeen = false),
            QuestionModel(id = 3, text = "Question 3", wasSeen = false),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomQuestions(3) } returns testQuestions
        val useCase = GetRandomTruthQuestionsUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(3)

        // Then
        assertEquals(3, result.size)
        assertEquals("Question 1", result[0].value)
        assertEquals("Question 2", result[1].value)
        assertEquals("Question 3", result[2].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomQuestions(3) }
    }

    @Test
    fun `invoke with count less than available returns subset of questions`() = runTest {
        // Given
        val testQuestions = listOf(
            QuestionModel(id = 1, text = "Question 1", wasSeen = false),
            QuestionModel(id = 2, text = "Question 2", wasSeen = false),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomQuestions(2) } returns testQuestions
        val useCase = GetRandomTruthQuestionsUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(2)

        // Then
        assertEquals(2, result.size)
        assertEquals("Question 1", result[0].value)
        assertEquals("Question 2", result[1].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomQuestions(2) }
    }

    @Test
    fun `invoke with count greater than available returns all questions`() = runTest {
        // Given
        val testQuestions = listOf(
            QuestionModel(id = 1, text = "Question 1", wasSeen = false),
            QuestionModel(id = 2, text = "Question 2", wasSeen = false),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomQuestions(5) } returns testQuestions
        val useCase = GetRandomTruthQuestionsUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(5)

        // Then
        assertEquals(2, result.size)
        assertEquals("Question 1", result[0].value)
        assertEquals("Question 2", result[1].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomQuestions(5) }
    }

    @Test
    fun `invoke with count zero returns empty list`() = runTest {
        // Given
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomQuestions(0) } returns emptyList()
        val useCase = GetRandomTruthQuestionsUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(0)

        // Then
        assertEquals(0, result.size)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomQuestions(0) }
    }

    @Test
    fun `invoke with non-English question maps correctly using fallback`() = runTest {
        // Given
        val testQuestions = listOf(
            QuestionModel(id = 1, text = "Question en français", wasSeen = false),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomQuestions(1) } returns testQuestions
        val useCase = GetRandomTruthQuestionsUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(1)

        // Then
        assertEquals(1, result.size)
        assertEquals("Question en français", result[0].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomQuestions(1) }
    }

    @Test
    fun `invoke with empty values map returns empty string`() = runTest {
        // Given
        val testQuestions = listOf(
            QuestionModel(id = 1, text = "", wasSeen = false),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomQuestions(1) } returns testQuestions
        val useCase = GetRandomTruthQuestionsUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(1)

        // Then
        assertEquals(1, result.size)
        assertEquals("", result[0].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomQuestions(1) }
    }
}
