package dev.adriankuta.partymania.model.repository.di.usecases.truthordare

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.adriankuta.partymania.domain.truthordare.GetRandomTruthQuestionsUseCase
import dev.adriankuta.partymania.model.repository.usecases.truthordare.GetRandomTruthQuestionsUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GetRandomTruthQuestionsUseCaseModule {

    @Binds
    abstract fun bind(
        getRandomTruthQuestionsUseCaseImpl: GetRandomTruthQuestionsUseCaseImpl,
    ): GetRandomTruthQuestionsUseCase
}
