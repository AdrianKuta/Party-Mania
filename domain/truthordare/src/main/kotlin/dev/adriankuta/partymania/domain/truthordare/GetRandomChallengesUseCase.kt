package dev.adriankuta.partymania.domain.truthordare

import dev.adriankuta.partymania.domain.truthordare.entity.Challenge

fun interface GetRandomChallengesUseCase {
    suspend operator fun invoke(count: Int): List<Challenge>
}
