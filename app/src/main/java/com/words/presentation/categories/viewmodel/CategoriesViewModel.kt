package com.words.presentation.categories.viewmodel

import androidx.lifecycle.viewModelScope
import com.words.domain.category.model.CategoryEntity
import com.words.domain.category.usecase.AddCategoryUseCase
import com.words.domain.category.usecase.DeleteCategoryUseCase
import com.words.domain.category.usecase.ReadAllCategoriesUseCase
import com.words.domain.category.usecase.UpdateCategoryUseCase
import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.categories.model.CategoriesModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val readAllCategoriesUseCase: ReadAllCategoriesUseCase
) :
    BaseViewModel<CategoriesUiState, CategoriesUiEvent, CategoriesUiEffect>(
        CategoriesUiState()
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

    override suspend fun handleEvent(event: CategoriesUiEvent) {
        when (event) {
            is CategoriesUiEvent.AddNewCategory -> {
                addCategoryUseCase.invoke(event.category)
            }
            is CategoriesUiEvent.UpdateCategory -> {
                updateCategoryUseCase.invoke(event.category)
            }
            is CategoriesUiEvent.DeleteNewCategory -> {
                deleteCategoryUseCase.invoke(event.category)
            }
        }
    }

    private suspend fun listOfCategories(): Flow<List<CategoryEntity>> =
        readAllCategoriesUseCase.invoke()

}