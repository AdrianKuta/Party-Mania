package dev.adriankuta.partymania.model.data.shared.shared.src.main.kotlin.dev.adriankuta.flights.model.data

import dev.adriankuta.partymania.model.datasource.shared.Cache

data class CacheImpl<T>(override val cacheKey: String?, override val data: T?) :
    Cache<T> {
    companion object {
        inline fun <reified T> emptyCache(): CacheImpl<T> =
            CacheImpl(null, null)

        inline fun <reified T> Cache<T>?.orEmpty(): Cache<T> =
            this ?: emptyCache()
    }
}
