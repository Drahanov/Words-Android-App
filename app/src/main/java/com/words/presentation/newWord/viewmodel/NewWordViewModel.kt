package com.words.presentation.newWord.viewmodel

import androidx.lifecycle.viewModelScope
import com.words.domain.category.model.CategoryEntity
import com.words.domain.category.usecase.ReadAllCategoriesUseCase
import com.words.domain.languages.model.LanguageEntity
import com.words.domain.languages.usecase.GetNativeLanguageUseCase
import com.words.domain.languages.usecase.ReadTargetsUseCase
import com.words.domain.words.model.WordEntity
import com.words.domain.words.repository.WordsRepository
import com.words.domain.words.usecase.TranslateWordUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.newWord.model.NewWordModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewWordViewModel @Inject constructor(
    private val translateWordUseCase: TranslateWordUseCase,
    private val readAllCategoriesUseCase: ReadAllCategoriesUseCase,
    private val wordsRepository: WordsRepository,
    private val getNativeLanguageUseCase: GetNativeLanguageUseCase,
    private val readTargetsUseCase: ReadTargetsUseCase
) :
    BaseViewModel<NewWordUiState, NewWordUiEvent, NewWordUiSideEffect>(NewWordUiState()) {

    private var translationProcess: Job = Job()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            listOfCategories().collect() {
                setState {
                    val categoriesResult = this.categories
                    categoriesResult.addAll(it)
                    copy(
                        categories = categoriesResult
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            nativeLanguage().collect() {
                setState {
                    copy(nativeLanguage = it)
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            studiedLanguages().collect() {
                setState {
                    copy(studiedLanguages = it, selectedLanguage = it[0].code)
                }
            }
        }
    }

    override suspend fun handleEvent(event: NewWordUiEvent) {
        when (event) {
            is NewWordUiEvent.AddNewWord -> {
                val word = WordEntity(
                    id = 0,
                    categoryId = uiState.value.selectedCategory,
                    languageId = 0,
                    word = uiState.value.word,
                    translation = uiState.value.translation,
                    addDate = Date(),
                    langEmoji = uiState.value.studiedLanguages.find {
                        it.code == uiState.value.selectedLanguage
                    }!!.emojiCode
                )
                wordsRepository.addWord(word)
            }
            is NewWordUiEvent.WordTyped -> {
                setState {
                    copy(isLoading = true, word = event.word)
                }
                if (translationProcess.isActive) translationProcess.cancelAndJoin()
                if (!event.word.isBlank() || event.word.isNotEmpty()) {
                    getTranslation(event.word)
                } else {
                    setState { copy(translation = event.word) }
                }
            }
            is NewWordUiEvent.CategorySelected -> {
                setState {
                    copy(selectedCategory = event.categoryId)
                }
            }
            is NewWordUiEvent.LanguageSelected -> {
                setState {
                    copy(selectedLanguage = event.languageCode)
                }
            }
        }
    }

    private fun getTranslation(word: String) {
        translationProcess = viewModelScope.launch {
            translateWordUseCase.invoke(
                word,
                uiState.value.selectedLanguage,
                uiState.value.nativeLanguage[0].code,
            )
                .enqueue(object : Callback<com.words.domain.words.model.TranslationEntity> {
                    override fun onResponse(
                        call: Call<com.words.domain.words.model.TranslationEntity>,
                        response: Response<com.words.domain.words.model.TranslationEntity>
                    ) {
                        if (response.body() != null) {
                            setEffect()
                            val result = response.body()!!.responseData.translatedText
                            if (result != null) {
                                setState {
                                    copy(isLoading = false, translation = result, isError = false)
                                }
                            }

                        } else {
                            setState { copy(isLoading = false, translation = "", isError = true) }
                        }
                    }

                    override fun onFailure(
                        call: Call<com.words.domain.words.model.TranslationEntity>,
                        t: Throwable
                    ) {
                        setState { copy(isLoading = false, translation = "", isError = true) }
                    }
                })

        }
    }

    private suspend fun listOfCategories(): Flow<List<CategoryEntity>> =
        readAllCategoriesUseCase.invoke()

    private suspend fun nativeLanguage(): Flow<List<LanguageEntity>> =
        getNativeLanguageUseCase.invoke()

    private suspend fun studiedLanguages(): Flow<List<LanguageEntity>> =
        readTargetsUseCase.invoke()
}
