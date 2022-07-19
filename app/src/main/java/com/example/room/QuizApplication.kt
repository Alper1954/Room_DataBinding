package com.example.room

import android.app.Application
import com.example.room.database.QuizDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class QuizApplication:Application() {
    val database by lazy { QuizDatabase.getDatabase(this) }
}