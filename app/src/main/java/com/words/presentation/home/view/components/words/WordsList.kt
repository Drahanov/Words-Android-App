package com.words.presentation.home.view.components.words

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.words.domain.category.model.CategoryEntity
import com.words.domain.words.model.WordEntity

@Composable
fun WordsList(
    words: List<WordEntity>,
    categories: List<CategoryEntity>,
    searchKeyword: String,
    onCheck: (word: WordEntity, isChecked: Boolean) -> Unit,
    onSwipe: (word: WordEntity) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = words) { word ->
            if (word.word.contains(searchKeyword) || searchKeyword.isEmpty() || searchKeyword.isBlank()) {
                WordItem(word = word, color = categories.find {
                    it.id == word.categoryId
                }!!.colorHex,
                    onCheck = { word, isChecked ->
                        onCheck.invoke(word, isChecked)
                    },
                    onSwipe = {
                        onSwipe.invoke(it)
                    })
            }
        }
    }
}