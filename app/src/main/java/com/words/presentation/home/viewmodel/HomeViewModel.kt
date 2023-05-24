package com.words.presentation.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.words.domain.category.model.CategoryEntity
import com.words.domain.category.usecase.ReadAllCategoriesUseCase
import com.words.domain.words.model.WordEntity
import com.words.domain.words.repository.WordsRepository
import com.words.domain.words.usecase.LearnWordUseCase
import com.words.domain.words.usecase.ReadAllWordsUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.home.model.HomeModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val readAllWordsUseCase: ReadAllWordsUseCase,
    private val repository: WordsRepository,
    private val readAllCategoriesUseCase: ReadAllCategoriesUseCase,
    private val wordLearnWordUseCase: LearnWordUseCase
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiSideEffect>(HomeUiState()) {

    private lateinit var categoriesJob: Job
    private lateinit var wordsJob: Job

    init {
        setState {
            copy(isLoading = true)
        }
        wordsJob = viewModelScope.launch(Dispatchers.IO) {
            listOfWords().collect() {
                setState {
                    copy(words = it, isLoading = categoriesJob.isCompleted)
                }
            }
        }

        categoriesJob = viewModelScope.launch(Dispatchers.IO) {
            listOfCategories().collect() {
                setState {
                    copy(categories = it, isLoading = wordsJob.isCompleted)
                }
            }

        }
    }

    override suspend fun handleEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ClickCategory -> {
//                val list = uiState.value.selectedCategories
//                if (uiState.value.selectedCategories.contains(event.id)) {
//                    list.remove(event.id)
//                } else {
//                    list.add(event.id)
//                }
//                setState {
//                    copy(selectedCategories = list)
//                }
            }
            is HomeUiEvent.SearchChange -> {
                setState {
                    copy(searchKeyword = event.keyword)
                }
            }

            is HomeUiEvent.RemoveWord -> {
                repository.removeWord(event.word)
            }

            is HomeUiEvent.WordLearned -> {
                val learnedWord = event.word
                learnedWord.isLearned = event.isChecked
                if (event.isChecked) {
                    learnedWord.learnDate = Date()
                } else {
                    learnedWord.learnDate = null
                }

                wordLearnWordUseCase.invoke(learnedWord)
            }
        }
    }


    private suspend fun listOfWords(): Flow<List<WordEntity>> = readAllWordsUseCase.invoke()

    private suspend fun listOfCategories(): Flow<List<CategoryEntity>> =
        readAllCategoriesUseCase.invoke()

}