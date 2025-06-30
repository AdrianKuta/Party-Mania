package dev.adriankuta.partymania.model.data.room.mappers

import dev.adriankuta.partymania.model.data.room.entities.QuestionEntity
import dev.adriankuta.partymania.model.datasource.questions.entities.QuestionModel

/**
 * Extension function to convert QuestionEntity to QuestionModel
 */
internal fun QuestionEntity.toModel(): QuestionModel {
    return QuestionModel(
        id = id,
        text = text,
        wasSeen = wasSeen,
    )
}
