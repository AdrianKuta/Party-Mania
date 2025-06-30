package dev.adriankuta.partymania.model.data.room.converters

import androidx.room.TypeConverter
import java.util.Locale

internal class LocaleConverter {
    @TypeConverter
    fun fromLocale(locale: Locale): String = locale.toLanguageTag()

    @TypeConverter
    fun toLocale(tag: String): Locale = Locale.forLanguageTag(tag)
}
