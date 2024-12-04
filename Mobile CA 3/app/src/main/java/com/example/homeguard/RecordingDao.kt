package com.example.homeguard

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordingDao {
    @Insert
    suspend fun insertRecording(recording: Recording)

    @Query("SELECT * FROM recordings")
    suspend fun getAllRecordings(): List<Recording>
    @Query("DELETE FROM recordings")
    suspend fun clearTable()
}