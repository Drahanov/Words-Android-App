package com.words.presentation.home.view.components.categories

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.words.domain.category.model.CategoryEntity

@Composable
fun CategoriesList(
    categories: List<CategoryEntity>,
    selected: List<Int>,
    onItemClick: (categoryId: Int) -> Unit
) {
    LazyRow(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = categories) { category ->
            CategoryItem(category, selected.contains(category.id)) {
                onItemClick.invoke(it)
            }
        }
    }
}