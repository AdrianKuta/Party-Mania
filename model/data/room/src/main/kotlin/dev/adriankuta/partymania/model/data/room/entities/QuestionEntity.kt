package dev.adriankuta.partymania.model.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
internal data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val values: Map<String, String>,
    val wasSeen: Boolean,
)
