package dev.adriankuta.partymania.model.datasource.questions.entities

data class QuestionModel(
    val id: Int = 0,
    val values: Map<String, String>,
    val wasSeen: Boolean,
) {
    // For backward compatibility and convenience
    val value: String
        get() = values["en"] ?: values.values.firstOrNull() ?: ""
}
