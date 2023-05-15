package com.words.presentation.statistics.view

import DonutChart
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.words.databinding.FragmentStatisticsBinding
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.statistics.model.StatisticsModel
import com.words.presentation.statistics.viewmodel.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

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
        binding.textViewToadyWords.text = state.newWordsToday.toString()
        binding.textViewLearnedToday.text = state.learnedToday.toString()
        binding.chart.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        }

        binding.chartCirlce.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DonutChart(indicatorValue = state.percentOfLearned)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}