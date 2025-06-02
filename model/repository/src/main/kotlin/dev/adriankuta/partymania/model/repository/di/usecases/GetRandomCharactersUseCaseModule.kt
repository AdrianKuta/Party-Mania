package dev.adriankuta.partymania.model.repository.di.usecases

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.adriankuta.partymania.domain.yesorno.GetRandomCharactersUseCase
import dev.adriankuta.partymania.model.repository.usecases.yesorno.GetRandomCharactersUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GetRandomCharactersUseCaseModule {

    @Binds
    abstract fun bind(
        getRandomCharactersUseCaseImpl: GetRandomCharactersUseCaseImpl
    ): GetRandomCharactersUseCase


}