package dev.adriankuta.partymania.model.repository.di.usecases.gametypes

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.adriankuta.partymania.domain.gametypes.GetGameTypesUseCase
import dev.adriankuta.partymania.model.repository.usecases.gametypes.GetGameTypesUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GetGameTypesUseCaseModule : GetGameTypesUseCase {

    @Binds
    abstract fun bind(
        getGameTypesUseCaseImpl: GetGameTypesUseCaseImpl,
    ): GetGameTypesUseCase
}
