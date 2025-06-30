package dev.adriankuta.partymania.model.datasource.questions.entities

data class QuestionModel(
    val id: Int = 0,
    val text: String,
    val wasSeen: Boolean,
)
