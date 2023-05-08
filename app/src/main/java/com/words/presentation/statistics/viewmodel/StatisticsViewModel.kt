package com.words.presentation.statistics.viewmodel

import androidx.lifecycle.viewModelScope
import com.words.domain.quiz.model.QuizEntity
import com.words.domain.quiz.repository.QuizRepository
import com.words.domain.words.model.WordEntity
import com.words.domain.words.usecase.ReadAllWordsUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.statistics.model.StatisticsModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val readAllWordsUseCase: ReadAllWordsUseCase,
    private val quizRepository: QuizRepository
) :
    BaseViewModel<StatisticsUiState, StatisticsUiEvent, StatisticsUiEffect>(
        StatisticsUiState()
    ) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            listOfWords().collect() {

                val today = it.filter {
                    it.addDate.date == Date().date
                }

                val learnedToday = it.filter {
                    it.isLearned
                }.filter {
                    it.learnDate?.date == Date().date
                }

                val learned = it.filter {
                    it.isLearned
                }.size

                setState {
                    copy(
                        words = it,
                        newWordsToday = today.size,
                        learnedToday = learnedToday.size,
                        percentOfLearned = ((learned.toDouble() / it.size) * 100).toInt()
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            listOfQuizes().collect() {
                setState {
                    copy(
                        quizes = it
                    )
                }
            }
        }
    }

    override suspend fun handleEvent(event: StatisticsUiEvent) {
    }

    private suspend fun listOfWords(): Flow<List<WordEntity>> =
        readAllWordsUseCase.invoke()

    private suspend fun listOfQuizes(): Flow<List<QuizEntity>> =
        quizRepository.readAllQuizes()

}