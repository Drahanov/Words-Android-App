package com.words.presentation.languages.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.words.R
import com.words.domain.languages.model.LanguageEntity
import com.words.domain.languages.usecase.ReadAllLanguagesUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.languages.model.LanguagesModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LanguagesViewModel @Inject constructor(
    private val readAllLanguagesUseCase: ReadAllLanguagesUseCase,
) :
    BaseViewModel<LanguagesUiState, LanguageUiEvent, LanguageUiEffect>(
        LanguagesUiState()
    ) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            listOfLanguages().collect() {
                setState {
                    copy(
                        languages = it.filter {
                            it.isHaveTranslation
                        },
                        selectedLanguageCode = Locale.getDefault().language
                    )
                }
            }
        }
    }

    override suspend fun handleEvent(event: LanguageUiEvent) {

    }

    private suspend fun listOfLanguages(): Flow<List<LanguageEntity>> =
        readAllLanguagesUseCase.invoke()
}