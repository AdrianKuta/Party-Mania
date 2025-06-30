package dev.adriankuta.partymania.model.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.adriankuta.partymania.model.data.room.converters.LocaleConverter
import dev.adriankuta.partymania.model.data.room.converters.MapTypeConverter
import dev.adriankuta.partymania.model.data.room.dao.ChallengesDao
import dev.adriankuta.partymania.model.data.room.dao.QuestionsDao
import dev.adriankuta.partymania.model.data.room.entities.ChallengeEntity
import dev.adriankuta.partymania.model.data.room.entities.QuestionEntity

@Database(
    entities = [
        QuestionEntity::class,
        ChallengeEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
@TypeConverters(MapTypeConverter::class, LocaleConverter::class)
internal abstract class PartyManiaDatabase : RoomDatabase() {
    abstract fun questionsDao(): QuestionsDao
    abstract fun challengesDao(): ChallengesDao
}
