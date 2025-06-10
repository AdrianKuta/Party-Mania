package dev.adriankuta.partymania.model.repository.usecases.gametypes

import dev.adriankuta.partymania.domain.gametypes.GetGameTypesUseCase
import dev.adriankuta.partymania.domain.gametypes.entities.GameType
import javax.inject.Inject

internal class GetGameTypesUseCaseImpl @Inject constructor() : GetGameTypesUseCase {
    override suspend fun invoke(): List<GameType> = GameType.entries
}
