package dev.adriankuta.partymania.model.data.simple.di.characters

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.adriankuta.partymania.core.util.IoDispatcher
import dev.adriankuta.partymania.model.data.simple.characters.CharactersDatasourceImpl
import dev.adriankuta.partymania.model.datasource.characters.CharactersDatasource
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
internal class CharactersDatasourceModule {

    @Provides
    fun provideCharactersDatasource(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ): CharactersDatasource {
        return CharactersDatasourceImpl(
            context = context,
            dispatcher = dispatcher,
        )
    }
}
