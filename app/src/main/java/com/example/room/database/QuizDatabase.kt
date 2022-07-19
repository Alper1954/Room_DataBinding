package com.example.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuizQuestion::class], version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {

    abstract val quizDataBaseDao: QuizDataBaseDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null
        fun getDatabase(
            context: Context,
        ): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                //Log.i("alain", "start getDatabase")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database.db"
                )
                    .createFromAsset("quiz_database.db")
                    .build()
                //Log.i("alain", "end getDatabase")
                INSTANCE = instance
                instance
            }
        }
    }
}