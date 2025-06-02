package dev.adriankuta.partymania.model.datasource.characters

import dev.adriankuta.partymania.model.datasource.characters.entities.CharacterModel

interface CharactersDatasource {
    suspend fun getRandomCharacters(count: Int): List<CharacterModel>
}