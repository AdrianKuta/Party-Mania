package dev.adriankuta.partymania.model.repository.mappers.challenges

import dev.adriankuta.partymania.domain.truthordare.entity.Challenge
import dev.adriankuta.partymania.model.datasource.questions.entities.ChallengeModel

internal fun ChallengeModel.toDomain(): Challenge {
    return Challenge(
        value = value,
    )
}
