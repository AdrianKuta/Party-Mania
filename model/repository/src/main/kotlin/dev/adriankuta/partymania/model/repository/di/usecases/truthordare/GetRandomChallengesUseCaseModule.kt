package dev.adriankuta.partymania.model.repository.di.usecases.truthordare

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.adriankuta.partymania.domain.truthordare.GetRandomChallengesUseCase
import dev.adriankuta.partymania.model.repository.usecases.truthordare.GetRandomChallengesUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GetRandomChallengesUseCaseModule {

    @Binds
    abstract fun bind(
        getRandomChallengesUseCaseImpl: GetRandomChallengesUseCaseImpl,
    ): GetRandomChallengesUseCase
}
