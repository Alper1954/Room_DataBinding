package com.example.room.screens

import androidx.lifecycle.*
import com.example.room.database.QuizDataBaseDao
import com.example.room.database.QuizQuestion
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class RoomViewModel(
    private val quizDataBaseDao: QuizDataBaseDao,
) : ViewModel() {

    private var listId = listOf<Int>()
    private var currentListId = mutableListOf<Int>()


    private val _currentQuestion: MutableLiveData<QuizQuestion> = MutableLiveData()
    val currentQuestion: LiveData<QuizQuestion>
        get() = _currentQuestion

    private val _answer = MutableLiveData<Int>()
    val answer: LiveData<Int>
        get() = _answer

    private val _goodAnswer = MutableLiveData<Int>()
    val goodAnswer: LiveData<Int>
        get() = _goodAnswer

    private val _state = MutableLiveData<Int>()
    val state: LiveData<Int>
        get() = _state

    init {
        _answer.value = 0
        _goodAnswer.value = 0
        _state.value = 0
    }

    fun allQuestions(): Flow<List<String>> = quizDataBaseDao.getAllQuestions()

    fun onAnswer(numAnswer:Int){
        _answer.value = numAnswer
        _state.value = 1
    }

    fun isListIdEmpty(): Boolean {
        return listId.isEmpty()
    }

    fun getNextQuestion() {
        _state.value = 0
        _answer.value = 0
        _goodAnswer.value = 0
        viewModelScope.launch {
            _currentQuestion.value = getCurrentQuestionAsync(nextQuestionId()).await()
        }
    }

    private fun nextQuestionId(): Int {
        if (currentListId.isEmpty()) {
            resetQuestionIdList()
        }
        return currentListId.removeAt(0)
    }

    private fun resetQuestionIdList() {
        currentListId = mutableListOf()
        currentListId.addAll(listId)
        currentListId.shuffle()

    }

    private fun getCurrentQuestionAsync(currentQuestionId: Int): Deferred<QuizQuestion?> =
        viewModelScope.async(Dispatchers.IO) {
            return@async quizDataBaseDao.getQuestion(
                currentQuestionId
            )
        }

    fun valid(): Boolean {
        if (_answer.value == 0) {
            return false
        } else {
            _goodAnswer.value = _currentQuestion.value!!.nbokAnswer
            _state.value = 2
            return true
        }
    }

    fun initListId(type: Int) {
        viewModelScope.launch {
            listId = getListIdAsync(type).await()
            currentListId = mutableListOf()
            currentListId.addAll(listId)
            currentListId.shuffle()
        }
    }

    private fun getListIdAsync(type: Int): Deferred<List<Int>> =
        viewModelScope.async(Dispatchers.IO) {
            if (type == 0) {
                return@async quizDataBaseDao.getListeId()
            } else {
                return@async quizDataBaseDao.getListeIdType(type)
            }
        }
}

class RoomViewModelFactory(
    private val quizDataBaseDao: QuizDataBaseDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(quizDataBaseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

