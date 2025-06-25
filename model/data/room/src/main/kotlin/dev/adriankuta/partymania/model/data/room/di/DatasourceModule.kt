package dev.adriankuta.partymania.model.data.room.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.adriankuta.partymania.model.data.room.QuestionsDatasourceImpl
import dev.adriankuta.partymania.model.data.room.dao.ChallengesDao
import dev.adriankuta.partymania.model.data.room.dao.QuestionsDao
import dev.adriankuta.partymania.model.datasource.questions.QuestionsDatasource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatasourceModule {

    @Provides
    @Singleton
    fun provideQuestionsDatasource(
        questionsDao: QuestionsDao,
        challengesDao: ChallengesDao,
        @ApplicationContext context: Context,
    ): QuestionsDatasource {
        val datasource = QuestionsDatasourceImpl(questionsDao, challengesDao, context)

        // Populate the database with initial data in a background coroutine
        CoroutineScope(Dispatchers.IO).launch {
            datasource.populateInitialData()
        }

        return datasource
    }
}
