package dev.adriankuta.partymania.data


import dev.adriankuta.partymania.domain.types.Character
import kotlinx.coroutines.flow.Flow

interface FamousCharactersRepository {

    suspend fun getRandomEntries(count: Int): List<Character>

    fun getRandomEntriesStream(count: Int): Flow<List<Character>>
}