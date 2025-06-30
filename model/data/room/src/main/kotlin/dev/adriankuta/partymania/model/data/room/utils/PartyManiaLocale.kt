package dev.adriankuta.partymania.model.data.room.utils

import java.util.Locale

internal object PartyManiaLocale {
    internal fun getDefaultAndSupported(): Locale =
        Locale.getDefault().takeIf(::isLanguageSupported) ?: Locale.ENGLISH
}

private fun isLanguageSupported(langCode: Locale): Boolean {
    val supportedLanguages = listOf("en", "pl", "es")
    return supportedLanguages.contains(langCode.language)
}
