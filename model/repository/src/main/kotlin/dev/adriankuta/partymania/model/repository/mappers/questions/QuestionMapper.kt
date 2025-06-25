package dev.adriankuta.partymania.model.repository.mappers.questions

import dev.adriankuta.partymania.domain.truthordare.entity.TruthQuestion
import dev.adriankuta.partymania.model.datasource.questions.entities.QuestionModel

internal fun QuestionModel.toDomain(): TruthQuestion {
    return TruthQuestion(
        value = value,
    )
}
