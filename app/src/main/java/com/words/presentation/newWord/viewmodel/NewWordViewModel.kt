package com.words.presentation.newWord.viewmodel

import androidx.lifecycle.viewModelScope
import com.words.domain.words.model.WordEntity
import com.words.domain.words.usecase.TranslateWordUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.newWord.model.NewWordModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewWordViewModel @Inject constructor(
    private val translateWordUseCase: TranslateWordUseCase
) :
    BaseViewModel<NewWordUiState, NewWordUiEvent, NewWordUiSideEffect>(NewWordUiState()) {

    var translationProcess: Job = Job()

    override suspend fun handleEvent(event: NewWordUiEvent) {
        when (event) {
            is NewWordUiEvent.AddNewWord -> {
//                getPopularMovies(event.word)
            }
            is NewWordUiEvent.WordTyped -> {
                setState {
                    if (translationProcess.isActive) {
                        translationProcess.cancel()
                    }

                    getPopularMovies(event.word)
                    copy(isLoading = true, word = event.word)
                }
            }
        }
    }

    private fun getPopularMovies(word: String) {
        translationProcess = viewModelScope.launch {
            translateWordUseCase.invoke(word).enqueue(object : Callback<WordEntity> {
                override fun onResponse(
                    call: Call<WordEntity>,
                    response: Response<WordEntity>
                ) {
                    if (response.body() != null) {
                        setEffect()
                        val result = response.body()!!.responseData.translatedText
                        setState {
                            copy(isLoading = false, translation = result)
                        }
                    } else {
                        setState { copy(isLoading = false, translation = "") }
                    }
                }

                override fun onFailure(call: Call<WordEntity>, t: Throwable) {
                    setState { copy(isLoading = false, translation = "") }
                }
            })

        }
    }
//        translationAPI.getTranslation()
//            .enqueue(object : Callback<WordEntity> {
//                override fun onResponse(call: Call<WordEntity>, response: Response<WordEntity>) {
//                    if (response.body() != null) {
//                        movieLiveData.value = response.body()!!.responseData
//                    } else {
//                        return
//                    }
//                }
//
//                override fun onFailure(call: Call<WordEntity>, t: Throwable) {
//                    Log.d("TAG", t.message.toString())
//                }
//            })
}
