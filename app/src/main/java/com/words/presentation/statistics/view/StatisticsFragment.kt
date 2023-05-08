package com.words.presentation.statistics.view

import DonutChart
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.words.databinding.FragmentStatisticsBinding
import com.words.domain.category.model.CategoryEntity
import com.words.domain.quiz.model.QuizEntity
import com.words.domain.words.model.WordEntity
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.home.model.HomeModel
import com.words.presentation.statistics.model.StatisticsModel
import com.words.presentation.statistics.viewmodel.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private val viewModel: StatisticsViewModel by viewModels()
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        return binding.root
    }

    private fun onHandleState(state: StatisticsModel.StatisticsUiState) {
        Toast.makeText(context, state.quizes.size.toString(), Toast.LENGTH_SHORT).show()
        binding.textViewToadyWords.text = state.newWordsToday.toString()
        binding.textViewLearnedToday.text = state.learnedToday.toString()
        if (!state.quizes.isEmpty()) binding.textView7.visibility = View.GONE
        binding.chart.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerViewWords()
            }
        }

        binding.chartCirlce.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DonutChart(indicatorValue = state.percentOfLearned)
            }
        }
    }

    @Composable
    private fun RecyclerViewWords(
        words: List<QuizEntity> = viewModel.uiState.value.quizes,

        ) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = words) { word ->
                WordItem(word = word, color = 0,
                    onCheck = { word, isChecked ->
                    },
                    onSwipe = {
                    })

            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun WordItem(
        word: QuizEntity,
        color: Long?,
        onSwipe: (id: WordEntity) -> Unit,
        onCheck: (id: WordEntity, isChecked: Boolean) -> Unit
    ) {
        val delete = SwipeAction(
            icon = rememberVectorPainter(Icons.TwoTone.Delete),
            background = Color.Red,
            onSwipe = {
//                onSwipe.invoke(word)
            }
        )

        val expanded = remember { mutableStateOf(false) }
        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false
        ) {
            Surface(
                color = if (true) Color(0xFFEBF0FF) else Color(0xFFDEE5FF),
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
                                style = if (false) TextStyle(
                                    textDecoration = TextDecoration.LineThrough
                                ) else TextStyle(textDecoration = TextDecoration.None),
                                text = word.date.date.toString() + " Apr",
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                                textAlign = TextAlign.Center,
                            )
                        }

                        Text(
                            style = if (false) TextStyle(
                                textDecoration = TextDecoration.LineThrough
                            ) else TextStyle(textDecoration = TextDecoration.None),
                            text = word.result.toString() + " / " + word.wordsAmount.toString(),
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(10.dp)
                                .weight(.2f),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}