package dev.adriankuta.partymania.model.datasource.shared

interface Cache<T> {
    val cacheKey: String?
    val data: T?
}
