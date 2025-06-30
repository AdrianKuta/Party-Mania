package dev.adriankuta.partymania.model.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Locale

@Entity(tableName = "challenges")
internal data class ChallengeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val locale: Locale,
    val text: String,
    val wasSeen: Boolean,
)
