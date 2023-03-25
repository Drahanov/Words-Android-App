package com.words.presentation.home.viewmodel

import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.home.model.HomeModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiSideEffect>(HomeUiState()) {

    override suspend fun handleEvent(event: HomeUiEvent) {

    }
}