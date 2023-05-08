package com.words.presentation.question.viewmodel

import androidx.lifecycle.viewModelScope
import com.words.domain.category.model.CategoryEntity
import com.words.domain.category.usecase.ReadAllCategoriesUseCase
import com.words.domain.category.usecase.UpdateCategoryUseCase
import com.words.domain.quiz.model.QuizEntity
import com.words.domain.quiz.repository.QuizRepository
import com.words.domain.words.model.WordEntity
import com.words.domain.words.usecase.ReadAllWordsUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.question.model.QuestionModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val readAllCategoriesUseCase: ReadAllCategoriesUseCase,
    private val readAllWordsUseCase: ReadAllWordsUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val quizRepository: QuizRepository
) :
    BaseViewModel<QuestionUiState, QuestionUiEvent, QuestionUiSideEffect>(
        QuestionUiState()
    ) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            listOfCategories().collect() {
                setState {
                    copy(categories = it.filter {
                        it.id != 0
                    })
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            listOfWords().collect() {
                setState {
                    copy(words = it, round = 1)
                }
            }
        }
    }

    override suspend fun handleEvent(event: QuestionUiEvent) {
        when (event) {
            QuestionUiEvent.Selected -> {
                var round = uiState.value.round
                round++
                if (round == 6) {
                    setEffect(QuestionUiSideEffect.ShowResult)
                } else {
                    setState {

                        copy(round = round)

                    }
                }
            }
            QuestionUiEvent.SaveNewQuiz -> {
                quizRepository.addNewQuiz(QuizEntity(0, Date(), 5, 5))
            }
        }
    }


    private suspend fun listOfCategories(): Flow<List<CategoryEntity>> =
        readAllCategoriesUseCase.invoke()

    private suspend fun listOfWords(): Flow<List<WordEntity>> =
        readAllWordsUseCase.invoke()
}