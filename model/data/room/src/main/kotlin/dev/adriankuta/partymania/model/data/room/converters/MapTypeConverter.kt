package dev.adriankuta.partymania.model.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import timber.log.Timber

/**
 * TypeConverter for converting between Map<String, String> and String for Room database.
 * This is used to store multilanguage values in the database.
 */
internal class MapTypeConverter {
    private val gson = Gson()
    private val type = object : TypeToken<Map<String, String>>() {}.type

    @TypeConverter
    fun fromString(value: String?): Map<String, String> {
        return if (value == null) {
            emptyMap()
        } else {
            try {
                gson.fromJson(value, type)
            } catch (e: JsonSyntaxException) {
                Timber.e(e)
                // If the value is not a valid JSON map, return a map with a default value
                mapOf("en" to value)
            }
        }
    }

    @TypeConverter
    fun toString(value: Map<String, String>?): String {
        return if (value == null) {
            ""
        } else {
            gson.toJson(value)
        }
    }
}
