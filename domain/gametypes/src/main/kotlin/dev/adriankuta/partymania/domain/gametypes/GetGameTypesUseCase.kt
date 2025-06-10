package dev.adriankuta.partymania.domain.gametypes

import dev.adriankuta.partymania.domain.gametypes.entities.GameType

fun interface GetGameTypesUseCase {
    suspend operator fun invoke(): List<GameType>
}
