package dev.adriankuta.partymania.model.data.simple.characters

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import dev.adriankuta.partymania.model.datasource.characters.CharactersDatasource
import dev.adriankuta.partymania.model.datasource.characters.entities.CharacterModel
import dev.adriankuta.partymania.model.datasource.characters.entities.FamousCharactersDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

internal class CharactersDatasourceImpl(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher,
) : CharactersDatasource {
    override suspend fun getRandomCharacters(count: Int): List<CharacterModel> {
        return withContext(dispatcher) {
            GsonBuilder()
                .create()

            val inputStream = context.assets.open("characters.json")
            val jsonReader = JsonReader(InputStreamReader(inputStream))
            val result =
                Gson().fromJson<FamousCharactersDto>(jsonReader, FamousCharactersDto::class.java)
            result.data
                .shuffled()
                .take(count)
        }
    }
}