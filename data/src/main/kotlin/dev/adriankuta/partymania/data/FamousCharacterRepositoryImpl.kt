package dev.adriankuta.partymania.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.adriankuta.partymania.core.util.IoDispatcher
import dev.adriankuta.partymania.data.dto.FamousCharactersDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import javax.inject.Inject


internal class FamousCharacterRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FamousCharactersRepository {
    override suspend fun getRandomEntries(count: Int): List<dev.adriankuta.partymania.core.model.Character> =
        withContext(dispatcher) {
            GsonBuilder()
                .create()

            val inputStream = context.assets.open("characters.json")
            val jsonReader = JsonReader(InputStreamReader(inputStream))
            val result = Gson().fromJson<FamousCharactersDto>(jsonReader, FamousCharactersDto::class.java)
            result.data
                .shuffled()
                .take(count)
        }

    override fun getRandomEntriesStream(count: Int): Flow<List<dev.adriankuta.partymania.core.model.Character>> {
        return flow {
            emit(getRandomEntries(20))
        }
    }
}