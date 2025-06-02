package dev.adriankuta.partymania.model.repository.usecases.yesorno

import dev.adriankuta.partymania.model.datasource.characters.CharactersDatasource
import dev.adriankuta.partymania.model.datasource.characters.entities.CharacterModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetRandomCharactersUseCaseImplTest {

    @Test
    fun `invoke returns mapped characters from datasource`() = runTest {
        // Given
        val testCharacters = listOf(
            CharacterModel("Character 1", "Category 1"),
            CharacterModel("Character 2", "Category 2"),
            CharacterModel("Character 3", "Category 3")
        )
        val mockDatasource = mockk<CharactersDatasource>()
        coEvery { mockDatasource.getRandomCharacters(3) } returns testCharacters
        val useCase = GetRandomCharactersUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(3)

        // Then
        assertEquals(3, result.size)
        assertEquals("Character 1", result[0].name)
        assertEquals("Category 1", result[0].category)
        assertEquals("Character 2", result[1].name)
        assertEquals("Category 2", result[1].category)
        assertEquals("Character 3", result[2].name)
        assertEquals("Category 3", result[2].category)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomCharacters(3) }
    }

    @Test
    fun `invoke with count less than available returns subset of characters`() = runTest {
        // Given
        val testCharacters = listOf(
            CharacterModel("Character 1", "Category 1"),
            CharacterModel("Character 2", "Category 2")
        )
        val mockDatasource = mockk<CharactersDatasource>()
        coEvery { mockDatasource.getRandomCharacters(2) } returns testCharacters
        val useCase = GetRandomCharactersUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(2)

        // Then
        assertEquals(2, result.size)
        assertEquals("Character 1", result[0].name)
        assertEquals("Category 1", result[0].category)
        assertEquals("Character 2", result[1].name)
        assertEquals("Category 2", result[1].category)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomCharacters(2) }
    }

    @Test
    fun `invoke with count greater than available returns all characters`() = runTest {
        // Given
        val testCharacters = listOf(
            CharacterModel("Character 1", "Category 1"),
            CharacterModel("Character 2", "Category 2")
        )
        val mockDatasource = mockk<CharactersDatasource>()
        coEvery { mockDatasource.getRandomCharacters(5) } returns testCharacters
        val useCase = GetRandomCharactersUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(5)

        // Then
        assertEquals(2, result.size)
        assertEquals("Character 1", result[0].name)
        assertEquals("Category 1", result[0].category)
        assertEquals("Character 2", result[1].name)
        assertEquals("Category 2", result[1].category)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomCharacters(5) }
    }

    @Test
    fun `invoke with count zero returns empty list`() = runTest {
        // Given
        val mockDatasource = mockk<CharactersDatasource>()
        coEvery { mockDatasource.getRandomCharacters(0) } returns emptyList()
        val useCase = GetRandomCharactersUseCaseImpl(mockDatasource)

        // When
        val result = useCase.invoke(0)

        // Then
        assertEquals(0, result.size)

        // Verify
        coVerify(exactly = 1) { mockDatasource.getRandomCharacters(0) }
    }

    @Test
    fun `invoke with null category in character model maps to null category in domain model`() =
        runTest {
            // Given
            val testCharacters = listOf(
                CharacterModel("Character 1", null)
            )
            val mockDatasource = mockk<CharactersDatasource>()
            coEvery { mockDatasource.getRandomCharacters(1) } returns testCharacters
            val useCase = GetRandomCharactersUseCaseImpl(mockDatasource)

            // When
            val result = useCase.invoke(1)

            // Then
            assertEquals(1, result.size)
            assertEquals("Character 1", result[0].name)
            assertEquals(null, result[0].category)

            // Verify
            coVerify(exactly = 1) { mockDatasource.getRandomCharacters(1) }
        }
}
