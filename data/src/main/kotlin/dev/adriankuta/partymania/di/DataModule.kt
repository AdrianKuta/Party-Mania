package dev.adriankuta.partymania.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.adriankuta.partymania.data.FamousCharacterRepositoryImpl
import dev.adriankuta.partymania.data.FamousCharactersRepository


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindFamousCharactersRepository(famousCharactersRepository: FamousCharacterRepositoryImpl): FamousCharactersRepository
}