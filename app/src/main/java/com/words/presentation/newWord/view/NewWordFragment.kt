package com.words.presentation.newWord.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.words.R
import com.words.databinding.FragmentNewWordBinding
import com.words.domain.category.model.CategoryEntity
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.newWord.model.NewWordModel
import com.words.presentation.newWord.model.NewWordModel.NewWordUiSideEffect
import com.words.presentation.newWord.model.NewWordModel.NewWordUiState
import com.words.presentation.newWord.view.adapters.CategoriesDropDownAdapter
import com.words.presentation.newWord.viewmodel.NewWordViewModel
import com.words.presentation.newWord.model.NewWordModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class NewWordFragment : BottomSheetDialogFragment() {

    private val viewModel: NewWordViewModel by viewModels()
    private var _binding: FragmentNewWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewWordBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        viewModel.sideEffect
            .onEach(::onHandleEffect)
            .observeIn(this)

        initAdapters()
        initListeners()

        return view
    }

    private fun initAdapters() {
        //mocked data
        val list = listOf(
            CategoryEntity("", "Food", ""),
            CategoryEntity("", "No category", ""),
            CategoryEntity("", "Default", ""),
            CategoryEntity("", "Professions", "")
        )

        val langs = listOf<String>(
            "\uD83C\uDDFA\uD83C\uDDE6",
            "\uD83C\uDDEC\uD83C\uDDE7",
            "\uD83C\uDDE7\uD83C\uDDEC"
        )
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_langs_dropdown, langs)
        binding.spinnerLanguage.setAdapter(arrayAdapter)

        val customDropDownAdapter = CategoriesDropDownAdapter(requireContext(), list)
        binding.spinnerCategory.adapter = customDropDownAdapter

    }

    private fun initListeners() {
        binding.buttonConfirmNewWord.setOnClickListener {
            viewModel.setEvent(NewWordUiEvent.AddNewWord(binding.editTextNewWord.text.toString()))
        }

        binding.editTextNewWord.addTextChangedListener {

            viewModel.setEvent(NewWordUiEvent.WordTyped(binding.editTextNewWord.text.toString()))
        }
    }

    private fun onHandleState(state: NewWordUiState) {
        binding.editTextTranslationAre.setText(state.translation)
        if (state.word != binding.editTextNewWord.text.toString()) {
            binding.editTextNewWord.setText(state.word)
        }
    }

    private fun onHandleEffect(effect: NewWordUiSideEffect) {
    }

}