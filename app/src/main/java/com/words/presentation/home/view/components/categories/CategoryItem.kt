package com.words.presentation.home.view.components.categories

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.words.domain.category.model.CategoryEntity

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryItem(
    category: CategoryEntity,
    isSelected: Boolean,
    onSelected: (id: Int) -> Unit
) {

    val expanded = remember { mutableStateOf(isSelected) }

    CompositionLocalProvider(
        LocalMinimumTouchTargetEnforcement provides false
    ) {
        Surface(
            color = if (!expanded.value) Color(0xFFDEE5FF) else Color(0xFF6078F3),
            modifier = Modifier.padding(end = 3.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                expanded.value = !expanded.value
                onSelected.invoke(category.id)
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(10.dp)
                        .width(10.dp),
                    color = Color(category.colorHex)
                ) {

                }
                Text(
                    text = category.title,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = if (!expanded.value) Color(0xFF787676) else Color.White,
                    modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                    textAlign = TextAlign.Center,
                )

            }

        }
    }
}
