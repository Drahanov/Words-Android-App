package com.words.presentation.targets.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.words.domain.category.model.CategoryEntity
import com.words.domain.languages.model.LanguageEntity
import com.words.domain.languages.usecase.ReadAllLanguagesUseCase
import com.words.domain.languages.usecase.UpdateLanguageUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.targets.model.Mode
import com.words.presentation.targets.model.TargetsModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TargetsViewModel @Inject constructor(
    private val readAllLanguagesUseCase: ReadAllLanguagesUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
) :
    BaseViewModel<TargetsUiState, TargetsUiEvent, TargetsUiEffect>(
        TargetsUiState()
    ) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            listOfLanguages().collect() {
                setState {
                    copy(
                        languages = it
                    )
                }
            }
        }
    }

    override suspend fun handleEvent(event: TargetsUiEvent) {
        when (event) {
            is TargetsUiEvent.LanguageSelected -> {
                val language = uiState.value.languages.find {
                    it.id == event.id
                }

                if (uiState.value.mode == Mode.NATIVE) {
                    for (i in uiState.value.languages.indices) {
                        uiState.value.languages[i].isNative = false
                    }

                    val languages = uiState.value.languages
                    languages[languages.indexOf(language)].isNative =
                        !languages[languages.indexOf(language)].isNative

                } else {
                    val languages = uiState.value.languages
                    languages[languages.indexOf(language)].isStudied =
                        !languages[languages.indexOf(language)].isStudied
                }

                setState {
                    copy(languages = languages, kostql = !kostql)
                }

            }
            TargetsUiEvent.Confirmed -> {

                if (uiState.value.mode == Mode.NATIVE) {
                    val language = uiState.value.languages.find {
                        it.isNative
                    }
                    if (language == null) {
                        setEffect(TargetsUiEffect.SelectItemUiEffect)
                    } else {
                        viewModelScope.launch(Dispatchers.IO) {
                            updateLanguageUseCase.invoke(language)
                        }
                        setState {
                            copy(mode = Mode.TARGETS)
                        }
                    }

                } else {
                    for (language in uiState.value.languages) {
                        if (language.isStudied) {
                            viewModelScope.launch(Dispatchers.IO) {
                                updateLanguageUseCase.invoke(language)
                            }
                        }
                    }
                    setEffect(TargetsUiEffect.LanguagesSelected)
                }
            }
        }
    }

    private suspend fun listOfLanguages(): Flow<List<LanguageEntity>> =
        readAllLanguagesUseCase.invoke()
}