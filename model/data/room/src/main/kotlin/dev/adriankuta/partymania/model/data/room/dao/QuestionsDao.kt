package dev.adriankuta.partymania.model.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.adriankuta.partymania.model.data.room.entities.QuestionEntity

@Dao
internal interface QuestionsDao {
    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomQuestions(count: Int): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE wasSeen = 0 ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomUnseenQuestions(count: Int): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Update
    suspend fun update(question: QuestionEntity)

    @Query("UPDATE questions SET wasSeen = 1 WHERE id IN (:ids)")
    suspend fun markAsSeen(ids: List<Int>)

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getCount(): Int
}
