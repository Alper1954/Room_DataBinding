package com.example.room.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDataBaseDao {

    @Query("SELECT question_text FROM quiz_question_table")
    fun getAllQuestions(): Flow<List<String>>

    @Query("SELECT questionId FROM quiz_question_table ORDER BY questionId")
    fun getListeId(): List<Int>

    @Query("SELECT questionId FROM quiz_question_table WHERE question_type =:type")
    fun getListeIdType(type: Int): List<Int>

    @Query("SELECT * FROM quiz_question_table WHERE questionId =:id" )
    fun getQuestion(id: Int): QuizQuestion?

}