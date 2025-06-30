package dev.adriankuta.partymania.model.data.room.mappers

import dev.adriankuta.partymania.model.data.room.entities.ChallengeEntity
import dev.adriankuta.partymania.model.datasource.questions.entities.ChallengeModel

/**
 * Extension function to convert ChallengeEntity to ChallengeModel
 */
internal fun ChallengeEntity.toModel(): ChallengeModel {
    return ChallengeModel(
        id = id,
        text = text,
        wasSeen = wasSeen,
    )
}
