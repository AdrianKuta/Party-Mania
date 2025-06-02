package dev.adriankuta.partymania.model.data.simple.characters

import android.content.Context
import android.content.res.AssetManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class CharactersDatasourceImplTest {

    private lateinit var context: Context
    private lateinit var assetManager: AssetManager
    private lateinit var datasource: CharactersDatasourceImpl
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        assetManager = mockk()
        context = mockk {
            every { assets } returns assetManager
        }
        datasource = CharactersDatasourceImpl(context, testDispatcher)
    }

    @Test
    fun `getRandomCharacters returns correct number of characters`() = runTest {
        // Given
        val jsonContent = """
            {
              "data": [
                {
                  "name": "Character 1"
                },
                {
                  "name": "Character 2",
                  "category": "Category 2"
                },
                {
                  "name": "Character 3"
                },
                {
                  "name": "Character 4",
                  "category": "Category 4"
                }
              ]
            }
        """.trimIndent()

        every { assetManager.open("characters.json") } returns ByteArrayInputStream(jsonContent.toByteArray())

        // When
        val result = datasource.getRandomCharacters(2)

        // Then
        assertEquals(2, result.size)
        verify { assetManager.open("characters.json") }
    }

    @Test
    fun `getRandomCharacters returns all characters when count is greater than available characters`() =
        runTest {
            // Given
            val jsonContent = """
            {
              "data": [
                {
                  "name": "Character 1"
                },
                {
                  "name": "Character 2",
                  "category": "Category 2"
                }
              ]
            }
        """.trimIndent()

            every { assetManager.open("characters.json") } returns ByteArrayInputStream(jsonContent.toByteArray())

            // When
            val result = datasource.getRandomCharacters(5)

            // Then
            assertEquals(2, result.size)
            verify { assetManager.open("characters.json") }
        }

    @Test
    fun `getRandomCharacters parses character data correctly`() = runTest {
        // Given
        val jsonContent = """
            {
              "data": [
                {
                  "name": "Character 1"
                },
                {
                  "name": "Character 2",
                  "category": "Category 2"
                }
              ]
            }
        """.trimIndent()

        every { assetManager.open("characters.json") } returns ByteArrayInputStream(jsonContent.toByteArray())

        // When
        val result = datasource.getRandomCharacters(2)

        // Then
        assertTrue(result.any { it.name == "Character 1" && it.category == null })
        assertTrue(result.any { it.name == "Character 2" && it.category == "Category 2" })
        verify { assetManager.open("characters.json") }
    }

    @Test
    fun `getRandomCharacters returns shuffled characters`() = runTest {
        // Given
        val jsonContent = """
            {
              "data": [
                {
                  "name": "Character 1"
                },
                {
                  "name": "Character 2"
                },
                {
                  "name": "Character 3"
                },
                {
                  "name": "Character 4"
                },
                {
                  "name": "Character 5"
                },
                {
                  "name": "Character 6"
                },
                {
                  "name": "Character 7"
                },
                {
                  "name": "Character 8"
                },
                {
                  "name": "Character 9"
                },
                {
                  "name": "Character 10"
                }
              ]
            }
        """.trimIndent()

        // We need to mock the asset manager for each call separately
        every { assetManager.open("characters.json") } returns ByteArrayInputStream(jsonContent.toByteArray()) andThen ByteArrayInputStream(
            jsonContent.toByteArray()
        )

        // When
        val result1 = datasource.getRandomCharacters(5)
        val result2 = datasource.getRandomCharacters(5)

        // Then
        // Note: There's a small chance this test could fail if the shuffling happens to produce the same order
        // In a real-world scenario, we might want to use a more deterministic approach for testing shuffling
        assertTrue(result1.map { it.name } != result2.map { it.name } || result1.size != result2.size)
        verify(exactly = 2) { assetManager.open("characters.json") }
    }
}
