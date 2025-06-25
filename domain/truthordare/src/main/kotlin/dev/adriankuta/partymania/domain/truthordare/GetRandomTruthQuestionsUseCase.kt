package dev.adriankuta.partymania.domain.truthordare

import dev.adriankuta.partymania.domain.truthordare.entity.TruthQuestion

fun interface GetRandomTruthQuestionsUseCase {
    suspend operator fun invoke(count: Int): List<TruthQuestion>
}
