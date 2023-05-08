package com.words.presentation.statistics.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun StatisticChart(
    data: List<Pair<String, Int>>,
    maxValue: Int,
) {

    val barGraphHeight by remember { mutableStateOf(150.dp) }
    val barGraphWidth by remember { mutableStateOf((10).dp) }


    Column(
        modifier = Modifier
            .padding(30.dp), verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barGraphHeight),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            data.forEach {
                Column(
                    modifier = Modifier
                        .padding(start = barGraphWidth, bottom = 5.dp)
                        .weight(1f)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxWidth()
                            .weight(0.5f)
                            .height((maxValue / it.second).dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF99A7EF),
                                        Color(0xFF6078F3)
                                    )
                                )
                            )
                    )

                    Text(
                        text = it.first,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    )
                }
            }
        }
    }

}

