package dev.adriankuta.partymania.model.data.room.dto

internal data class QuestionDto(
    val values: Map<String, String>,
    val wasSeen: Boolean,
)
