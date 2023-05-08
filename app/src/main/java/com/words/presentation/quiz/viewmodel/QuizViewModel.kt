package com.words.presentation.quiz.viewmodel

import androidx.lifecycle.viewModelScope
import com.words.domain.category.model.CategoryEntity
import com.words.domain.category.usecase.ReadAllCategoriesUseCase
import com.words.domain.category.usecase.UpdateCategoryUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.quiz.model.QuizModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val readAllCategoriesUseCase: ReadAllCategoriesUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) :


    BaseViewModel<QuizUiState, QuizUiEvent, QuizUiSideEffect>(
        QuizUiState()
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
    }

    override suspend fun handleEvent(event: QuizUiEvent) {
        when (event) {
            is QuizUiEvent.CategorySelected -> {
                val category = event.category
                category.isSelectedForQuiz = !category.isSelectedForQuiz

                updateCategoryUseCase.invoke(category)
                setState {
                    copy(kostql = !kostql)
                }

            }
        }
    }

    private suspend fun listOfCategories(): Flow<List<CategoryEntity>> =
        readAllCategoriesUseCase.invoke()

}