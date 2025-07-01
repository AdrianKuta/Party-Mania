package dev.adriankuta.partymania.model.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.adriankuta.partymania.model.data.room.entities.QuestionEntity
import java.util.Locale

@Dao
internal interface QuestionsDao {
    @Query("SELECT * FROM questions WHERE locale = :locale ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomQuestions(
        count: Int,
        locale: Locale = Locale.ENGLISH,
    ): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE wasSeen = 0 AND locale = :locale ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomUnseenQuestions(
        count: Int,
        locale: Locale = Locale.ENGLISH,
    ): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Update
    suspend fun update(question: QuestionEntity)

    @Query("UPDATE questions SET wasSeen = 1 WHERE id IN (:ids)")
    suspend fun markAsSeen(ids: List<Int>)

    @Query("SELECT COUNT(*) FROM questions WHERE locale = :locale")
    suspend fun getCount(locale: Locale = Locale.ENGLISH): Int
}
