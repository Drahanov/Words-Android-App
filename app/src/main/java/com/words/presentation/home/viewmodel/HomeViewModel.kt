package com.words.presentation.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.words.domain.words.model.WordEntity
import com.words.domain.words.usecase.ReadAllWordsUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.home.model.HomeModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val readAllWordsUseCase: ReadAllWordsUseCase,
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiSideEffect>(HomeUiState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fullSchedule().collect() {
                setState {
                    copy(words = it)
                }
            }
        }
    }

    override suspend fun handleEvent(event: HomeUiEvent) {

    }

    private suspend fun fullSchedule(): Flow<List<WordEntity>> = readAllWordsUseCase.invoke()

}