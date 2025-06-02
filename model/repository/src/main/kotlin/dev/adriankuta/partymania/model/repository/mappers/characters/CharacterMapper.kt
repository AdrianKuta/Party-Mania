package dev.adriankuta.partymania.model.repository.mappers.characters

import dev.adriankuta.partymania.domain.types.Character
import dev.adriankuta.partymania.model.datasource.characters.entities.CharacterModel

internal fun CharacterModel.toDomain(): Character {
    return Character(
        name = name,
        category = category,
    )
}
