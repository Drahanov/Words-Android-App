package com.words.presentation.home.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.words.R
import com.words.databinding.FragmentHomeBinding
import com.words.domain.category.model.CategoryEntity
import com.words.domain.words.model.WordEntity
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.home.model.HomeModel.*
import com.words.presentation.home.view.components.categories.CategoriesList
import com.words.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import com.words.presentation.home.view.components.words.WordItem
import com.words.presentation.home.view.components.words.WordsList

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        isTargetsSelected()

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        initComposeView()

        return view
    }

    private fun initComposeView() {

        binding.rvCategories.apply {
            setContent {
                val state = viewModel.uiState.collectAsState().value
                CategoriesList(
                    categories = state.categories,
                    selected = state.selectedCategories
                ) {
                    viewModel.setEvent(HomeUiEvent.ClickCategory(it))
                }
            }
        }

        binding.rvWords.apply {
            setContent {
                val state = viewModel.uiState.collectAsState().value

                WordsList(
                    words = state.words,
                    categories = state.categories,
                    searchKeyword = state.searchKeyword,
                    onCheck = { word, isChecked ->
                        viewModel.setEvent(HomeUiEvent.WordLearned(word, isChecked))
                    },
                    onSwipe = {
                        viewModel.setEvent(HomeUiEvent.RemoveWord(it))
                    }
                )
            }
        }
    }

    private fun isTargetsSelected(): Boolean {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return false
        return sharedPref.getBoolean(getString(R.string.isTargetsSelect), false)
    }

    private fun initListeners() {
        binding.buttonNewWord.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(
                R.id.homeFragmentToNewWordDialog
            )
        }

        binding.editTextSearch.addTextChangedListener {
            viewModel.setEvent(HomeUiEvent.SearchChange(it.toString()))
        }
    }

    private fun onHandleState(state: HomeUiState) {
        if (state.words.isEmpty()) {
            binding.imageNoData.visibility = View.VISIBLE
        } else {
            binding.imageNoData.visibility = View.GONE
        }
    }
}