package com.words.presentation.quiz.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.words.QuizActivity
import com.words.databinding.FragmentQuizBinding
import com.words.domain.category.model.CategoryEntity
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.quiz.model.QuizModel
import com.words.presentation.quiz.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import nl.dionsegijn.steppertouch.OnStepCallback


@AndroidEntryPoint
class QuizFragment : Fragment() {


    private val viewModel: QuizViewModel by viewModels()
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        binding.rvCategories.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerViewCategories()
            }
        }

        binding.stepperTouch2.apply {
            setContent {
                CustomSwitch()
            }
        }

        val stepperTouch = binding.stepperTouch
        stepperTouch.minValue = 5
        stepperTouch.minValue = 25
        stepperTouch.sideTapEnabled = true
        stepperTouch.addStepCallback(object : OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
            }
        })

        binding.buttonStartQuiz.setOnClickListener {
            val intent = Intent(activity, QuizActivity::class.java)
            intent.putExtra("wordsAmountKey", binding.stepperTouch.count)
            intent.putExtra("includeLearnedKey", true)

            startActivity(intent)
        }

        return binding.root
    }

    private fun onHandleState(state: QuizModel.QuizUiState) {
        binding.rvCategories.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerViewCategories()
            }
        }
    }

    @Composable
    private fun RecyclerViewCategories(
        categories: List<CategoryEntity> = viewModel.uiState.value.categories,
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            items(items = categories) { category ->
                CategoryItem(
                    selected = category.isSelectedForQuiz,
                    title = category.title,
                    onClick = {
                        viewModel.setEvent(QuizModel.QuizUiEvent.CategorySelected(category))
                    },
                    categoryColor = category.colorHex
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    @Composable
    fun CustomSwitch(
        width: Dp = 72.dp,
        height: Dp = 40.dp,
        checkedTrackColor: Color = Color(0xFF6078F3),
        uncheckedTrackColor: Color = Color(0xFFe0e0e0),
        gapBetweenThumbAndTrackEdge: Dp = 8.dp,
        borderWidth: Dp = 2.dp,
        cornerSize: Int = 50,
        iconInnerPadding: Dp = 4.dp,
        thumbSize: Dp = 24.dp
    ) {

        // this is to disable the ripple effect
        val interactionSource = remember {
            MutableInteractionSource()
        }

        // state of the switch
        var switchOn by remember {
            mutableStateOf(true)
        }

        // for moving the thumb
        val alignment by animateAlignmentAsState(if (switchOn) 1f else -1f)

        // outer rectangle with border
        Box(
            modifier = Modifier
                .size(width = width, height = height)
                .border(
                    width = borderWidth,
                    color = if (switchOn) checkedTrackColor else uncheckedTrackColor,
                    shape = RoundedCornerShape(percent = cornerSize)
                )
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    switchOn = !switchOn
                },
            contentAlignment = Alignment.Center
        ) {

            // this is to add padding at the each horizontal side
            Box(
                modifier = Modifier
                    .padding(
                        start = gapBetweenThumbAndTrackEdge,
                        end = gapBetweenThumbAndTrackEdge
                    )
                    .fillMaxSize(),
                contentAlignment = alignment
            ) {

                // thumb with icon
                Icon(
                    imageVector = if (switchOn) Icons.Filled.Done else Icons.Filled.Close,
                    contentDescription = if (switchOn) "Enabled" else "Disabled",
                    modifier = Modifier
                        .size(size = thumbSize)
                        .background(
                            color = if (switchOn) checkedTrackColor else uncheckedTrackColor,
                            shape = CircleShape
                        )
                        .padding(all = iconInnerPadding),
                    tint = Color.White
                )
            }
        }

        // gap between switch and the text
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    private fun animateAlignmentAsState(
        targetBiasValue: Float
    ): State<BiasAlignment> {
        val bias by animateFloatAsState(targetBiasValue)
        return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}