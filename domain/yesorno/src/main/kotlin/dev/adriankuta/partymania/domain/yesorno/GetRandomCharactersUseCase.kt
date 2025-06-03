package dev.adriankuta.partymania.domain.yesorno

import dev.adriankuta.partymania.domain.types.Character

fun interface GetRandomCharactersUseCase {
    suspend operator fun invoke(count: Int): List<Character>
}
