package dev.adriankuta.partymania.data


import kotlinx.coroutines.flow.Flow

interface FamousCharactersRepository {

    suspend fun getRandomEntries(count: Int): List<dev.adriankuta.partymania.core.model.Character>

    fun getRandomEntriesStream(count: Int): Flow<List<dev.adriankuta.partymania.core.model.Character>>
}