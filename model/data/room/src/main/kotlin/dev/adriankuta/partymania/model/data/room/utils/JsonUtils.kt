package dev.adriankuta.partymania.model.data.room.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import timber.log.Timber
import java.io.InputStreamReader

/**
 * Utility class for JSON operations
 */
internal object JsonUtils {

    /**
     * Parse JSON from assets file into a list of objects
     *
     * @param context Android context
     * @param fileName Name of the file in assets directory
     * @param typeToken Type token for parsing JSON to objects
     * @return List of parsed objects or empty list if parsing fails
     */
    @Suppress("TooGenericExceptionCaught")
    inline fun <reified T, R> parseJsonFromAssets(
        context: Context,
        fileName: String,
        typeToken: TypeToken<T>,
        crossinline mapper: (T) -> R,
    ): R? {
        return try {
            val inputStream = context.assets.open(fileName)
            val jsonReader = JsonReader(InputStreamReader(inputStream))
            val content = Gson().fromJson<T>(jsonReader, typeToken.type)
            mapper(content)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}
