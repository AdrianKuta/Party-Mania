package dev.adriankuta.partymania.model.data.room.mappers

import dev.adriankuta.partymania.model.data.room.dto.ChallengeDto
import dev.adriankuta.partymania.model.data.room.entities.ChallengeEntity
import dev.adriankuta.partymania.model.datasource.questions.entities.ChallengeModel

/**
 * Extension function to convert ChallengeEntity to ChallengeModel
 */
internal fun ChallengeEntity.toModel(): ChallengeModel {
    return ChallengeModel(
        id = id,
        values = values,
        wasSeen = wasSeen,
    )
}

/**
 * Extension function to convert ChallengeModel to ChallengeEntity
 */
internal fun ChallengeModel.toEntity(): ChallengeEntity {
    return ChallengeEntity(
        id = id,
        values = values,
        wasSeen = wasSeen,
    )
}

/**
 * Extension function to convert ChallengeDto to ChallengeEntity
 */
internal fun ChallengeDto.toEntity(): ChallengeEntity {
    return ChallengeEntity(
        values = values,
        wasSeen = wasSeen,
    )
}
