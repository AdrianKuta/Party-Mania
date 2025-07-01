package dev.adriankuta.partymania.model.data.room.dto

internal data class ContentDto(
    /**
     * An IETF BCP 47 language tag
     * https://en.wikipedia.org/wiki/IETF_language_tag
     */
    val languageTag: String,
    val truth: List<String>,
    val dare: List<String>,
)
