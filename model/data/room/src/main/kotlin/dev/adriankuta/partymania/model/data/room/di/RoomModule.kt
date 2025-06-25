package dev.adriankuta.partymania.model.data.room.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.adriankuta.partymania.model.data.room.PartyManiaDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PartyManiaDatabase {
        return Room.databaseBuilder(
            context,
            PartyManiaDatabase::class.java,
            "partymania-database",
        )
            .build()
    }
}
