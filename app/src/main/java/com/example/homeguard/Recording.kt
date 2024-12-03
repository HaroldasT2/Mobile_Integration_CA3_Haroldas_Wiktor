package com.example.homeguard

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordings")
data class Recording(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: String,
    val description: String,
    val imageUrl: String
)