package dev.adriankuta.partymania.model.repository.usecases.truthordare

import dev.adriankuta.partymania.model.datasource.questions.QuestionsDatasource
import dev.adriankuta.partymania.model.datasource.questions.entities.ChallengeModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRandomChallengesUseCaseImplTest {

    @Test
    fun `invoke returns mapped challenges from datasource`() = runTest {
        // Given
        val testChallenges = listOf(
            ChallengeModel(id = 1, values = mapOf("en" to "Challenge 1"), wasSeen = false),
            ChallengeModel(id = 2, values = mapOf("en" to "Challenge 2"), wasSeen = false),
            ChallengeModel(id = 3, values = mapOf("en" to "Challenge 3"), wasSeen = false),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomChallenges(3) } returns testChallenges
        val useCase = GetRandomChallengesUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(3)

        // Then
        assertEquals(3, result.size)
        assertEquals("Challenge 1", result[0].value)
        assertEquals("Challenge 2", result[1].value)
        assertEquals("Challenge 3", result[2].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomChallenges(3) }
    }

    @Test
    fun `invoke with count less than available returns subset of challenges`() = runTest {
        // Given
        val testChallenges = listOf(
            ChallengeModel(id = 1, values = mapOf("en" to "Challenge 1"), wasSeen = false),
            ChallengeModel(id = 2, values = mapOf("en" to "Challenge 2"), wasSeen = false),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomChallenges(2) } returns testChallenges
        val useCase = GetRandomChallengesUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(2)

        // Then
        assertEquals(2, result.size)
        assertEquals("Challenge 1", result[0].value)
        assertEquals("Challenge 2", result[1].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomChallenges(2) }
    }

    @Test
    fun `invoke with count greater than available returns all challenges`() = runTest {
        // Given
        val testChallenges = listOf(
            ChallengeModel(id = 1, values = mapOf("en" to "Challenge 1"), wasSeen = false),
            ChallengeModel(id = 2, values = mapOf("en" to "Challenge 2"), wasSeen = false),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomChallenges(5) } returns testChallenges
        val useCase = GetRandomChallengesUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(5)

        // Then
        assertEquals(2, result.size)
        assertEquals("Challenge 1", result[0].value)
        assertEquals("Challenge 2", result[1].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomChallenges(5) }
    }

    @Test
    fun `invoke with count zero returns empty list`() = runTest {
        // Given
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomChallenges(0) } returns emptyList()
        val useCase = GetRandomChallengesUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(0)

        // Then
        assertEquals(0, result.size)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomChallenges(0) }
    }

    @Test
    fun `invoke with non-English challenge maps correctly using fallback`() = runTest {
        // Given
        val testChallenges = listOf(
            ChallengeModel(
                id = 1,
                values = mapOf("fr" to "Challenge en français"),
                wasSeen = false,
            ),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomChallenges(1) } returns testChallenges
        val useCase = GetRandomChallengesUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(1)

        // Then
        assertEquals(1, result.size)
        assertEquals("Challenge en français", result[0].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomChallenges(1) }
    }

    @Test
    fun `invoke with empty values map returns empty string`() = runTest {
        // Given
        val testChallenges = listOf(
            ChallengeModel(id = 1, values = emptyMap(), wasSeen = false),
        )
        val mockDatasource = mockk<QuestionsDatasource>()
        coEvery { mockDatasource.getRandomChallenges(1) } returns testChallenges
        val useCase = GetRandomChallengesUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(1)

        // Then
        assertEquals(1, result.size)
        assertEquals("", result[0].value)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomChallenges(1) }
    }
}
