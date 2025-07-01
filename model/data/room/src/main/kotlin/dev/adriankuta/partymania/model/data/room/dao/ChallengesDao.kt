package dev.adriankuta.partymania.model.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.adriankuta.partymania.model.data.room.entities.ChallengeEntity
import java.util.Locale

@Dao
internal interface ChallengesDao {
    @Query("SELECT * FROM challenges WHERE locale = :locale ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomChallenges(
        count: Int,
        locale: Locale = Locale.ENGLISH,
    ): List<ChallengeEntity>

    @Query("SELECT * FROM challenges WHERE wasSeen = 0 AND locale = :locale ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomUnseenChallenges(
        count: Int,
        locale: Locale = Locale.ENGLISH,
    ): List<ChallengeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(challenges: List<ChallengeEntity>)

    @Update
    suspend fun update(challenge: ChallengeEntity)

    @Query("UPDATE challenges SET wasSeen = 1 WHERE id IN (:ids)")
    suspend fun markAsSeen(ids: List<Int>)

    @Query("SELECT COUNT(*) FROM challenges WHERE locale = :locale")
    suspend fun getCount(locale: Locale = Locale.ENGLISH): Int
}
