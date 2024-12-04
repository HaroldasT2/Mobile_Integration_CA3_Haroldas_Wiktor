package com.example.homeguard

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import android.util.Log

@Database(entities = [Recording::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordingDao(): RecordingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "homeguard_database"
                )
                    .fallbackToDestructiveMigration() // Clears the database on schema change
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    Log.d("AppDatabase", "Populating database...")
                    populateDatabase(database.recordingDao())
                }
            }
        }

        suspend fun populateDatabase(recordingDao: RecordingDao) {
            recordingDao.clearTable()

            val recording1 = Recording(
                timestamp = "2024-12-02 10:00",
                description = "Sample recording 1",
                imageUrl = "https://www.cnet.com/a/img/resize/5b94304f2e405e23712a6c3ef2ff05e24365fa33/hub/2023/03/08/92644bdd-ebca-4994-b802-8b16dcc2be46/ring-battery-doorbell-plus.jpg?auto=webp&fit=crop&height=675&width=1200"
            )
            val recording2 = Recording(
                timestamp = "2024-12-02 11:00",
                description = "Sample recording 2",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCy2pScPVvWaHV59T9Gtn443GFEnPM_tNzZg&s"
            )

            Log.d("AppDatabase", "Inserting: $recording1")
            Log.d("AppDatabase", "Inserting: $recording2")
            recordingDao.insertRecording(recording1)
            recordingDao.insertRecording(recording2)
        }
    }
}