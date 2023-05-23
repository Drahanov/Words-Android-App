package com.words.presentation.home.view.components.words

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.words.domain.words.model.WordEntity
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WordItem(
    word: WordEntity,
    color: Long?,
    onSwipe: (id: WordEntity) -> Unit,
    onCheck: (id: WordEntity, isChecked: Boolean) -> Unit
) {
    val delete = SwipeAction(
        icon = rememberVectorPainter(Icons.TwoTone.Delete),
        background = Color.Red,
        onSwipe = {
            onSwipe.invoke(word)
        }
    )
    val checkedState = remember {
        mutableStateOf(word.isLearned)
    }
    val expanded = remember { mutableStateOf(false) }
    CompositionLocalProvider(
        LocalMinimumTouchTargetEnforcement provides false
    ) {
        Surface(
            color = if (checkedState.value) Color(0xFFEBF0FF) else Color(0xFFDEE5FF),
            modifier = Modifier.padding(bottom = 5.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                expanded.value = !expanded.value
            }
        ) {


            SwipeableActionsBox(endActions = listOf(delete), swipeThreshold = 100.dp) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .height(70.dp)
                            .width(10.dp),
                        color = if (color != null) Color(color) else Color.Blue
                    ) {}

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .weight(1f)
                    ) {

                        Text(
                            style = if (checkedState.value) TextStyle(
                                textDecoration = TextDecoration.LineThrough
                            ) else TextStyle(textDecoration = TextDecoration.None),
                            text = word.word,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            style = if (checkedState.value) TextStyle(
                                textDecoration = TextDecoration.LineThrough
                            ) else TextStyle(textDecoration = TextDecoration.None),
                            text = word.translation,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xff787676),
                            modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                            textAlign = TextAlign.Center,
                        )

                    }

                    Text(
                        style = if (checkedState.value) TextStyle(
                            textDecoration = TextDecoration.LineThrough
                        ) else TextStyle(textDecoration = TextDecoration.None),
                        text = word.langEmoji,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(.2f),
                        textAlign = TextAlign.Center,
                    )

                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = {
                            checkedState.value = it
                            onCheck.invoke(word, it)
                        },
                        Modifier
                            .padding(10.dp)
                            .weight(.2f),
                        colors = checkBoxColors()
                    )
                }
            }
        }
    }
}

@Composable
fun checkBoxColors(): CheckboxColors {
    return CheckboxDefaults.colors(
        checkedColor = Color(0xFF6078F3),
    )
}