package dev.adriankuta.partymania.model.repository.utilities

import dev.adriankuta.partymania.model.datasource.shared.Cache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal fun <T, R> loadData(
    onCacheInvalidated: suspend (cacheKey: String) -> Unit,
    observeCache: () -> Flow<Cache<T>>,
    mapToDomain: suspend (T?) -> R?,
): Flow<R?> {
    return observeCache().distinctUntilChanged().map {
        if (it.cacheKey == null) {
            val refreshTime = Clock.System.now()
            onCacheInvalidated(it.cacheKey ?: refreshTime.toEpochMilliseconds().toString())
        }
        mapToDomain(it.data)
    }
}
