package dev.adriankuta.partymania.model.repository.usecases.yesorno

import dev.adriankuta.partymania.domain.types.Character
import dev.adriankuta.partymania.domain.yesorno.GetRandomCharactersUseCase
import dev.adriankuta.partymania.model.datasource.characters.CharactersDatasource
import dev.adriankuta.partymania.model.repository.mappers.characters.toDomain
import javax.inject.Inject

internal class GetRandomCharactersUseCaseImpl @Inject constructor(
    private val charactersDatasource: CharactersDatasource
) : GetRandomCharactersUseCase {
    override suspend fun invoke(count: Int): List<Character> {
        return charactersDatasource.getRandomCharacters(count).map {
            it.toDomain()
        }
    }
}
