package dev.adriankuta.partymania.model.data.room.mappers

import dev.adriankuta.partymania.model.data.room.dto.QuestionDto
import dev.adriankuta.partymania.model.data.room.entities.QuestionEntity
import dev.adriankuta.partymania.model.datasource.questions.entities.QuestionModel

/**
 * Extension function to convert QuestionEntity to QuestionModel
 */
internal fun QuestionEntity.toModel(): QuestionModel {
    return QuestionModel(
        id = id,
        values = values,
        wasSeen = wasSeen,
    )
}

/**
 * Extension function to convert QuestionModel to QuestionEntity
 */
internal fun QuestionModel.toEntity(): QuestionEntity {
    return QuestionEntity(
        id = id,
        values = values,
        wasSeen = wasSeen,
    )
}

/**
 * Extension function to convert QuestionDto to QuestionEntity
 */
internal fun QuestionDto.toEntity(): QuestionEntity {
    return QuestionEntity(
        values = values,
        wasSeen = wasSeen,
    )
}
