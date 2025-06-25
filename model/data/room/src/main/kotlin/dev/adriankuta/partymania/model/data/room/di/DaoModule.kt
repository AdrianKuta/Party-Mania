package dev.adriankuta.partymania.model.data.room.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.adriankuta.partymania.model.data.room.PartyManiaDatabase
import dev.adriankuta.partymania.model.data.room.dao.ChallengesDao
import dev.adriankuta.partymania.model.data.room.dao.QuestionsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    @Singleton
    fun provideQuestionsDao(database: PartyManiaDatabase): QuestionsDao {
        return database.questionsDao()
    }

    @Provides
    @Singleton
    fun provideChallengesDao(database: PartyManiaDatabase): ChallengesDao {
        return database.challengesDao()
    }
}
