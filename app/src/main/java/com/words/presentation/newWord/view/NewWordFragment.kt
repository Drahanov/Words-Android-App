package com.words.presentation.newWord.view

import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.words.R
import com.words.databinding.FragmentNewWordBinding
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.newWord.model.NewWordModel.*
import com.words.presentation.newWord.view.adapters.CategoriesDropDownAdapter
import com.words.presentation.newWord.viewmodel.NewWordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NewWordFragment : BottomSheetDialogFragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: NewWordViewModel by viewModels()
    private var _binding: FragmentNewWordBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoriesAdapter: CategoriesDropDownAdapter
    private lateinit var studiedAdapter: ArrayAdapter<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        initListeners()
    }


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


        return view
    }

    private fun initAdapters() {

        binding.spinnerCategory.setPopupBackgroundResource(R.drawable.background_edit_text)
        binding.spinnerLanguage.setPopupBackgroundResource(R.drawable.background_edit_text)
        binding.spinnerLanguageTo.setPopupBackgroundResource(R.drawable.background_edit_text)

        categoriesAdapter =
            CategoriesDropDownAdapter(requireContext(), viewModel.uiState.value.categories)
        binding.spinnerCategory.adapter = categoriesAdapter

        studiedAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_langs_dropdown,
            viewModel.uiState.value.studiedLanguages.map { it.emojiCode })

        binding.spinnerLanguageTo.adapter = studiedAdapter

    }

    private fun initListeners() {
        binding.buttonConfirmNewWord.setOnClickListener {
            viewModel.setEvent(NewWordUiEvent.AddNewWord)
        }

        binding.editTextNewWord.addTextChangedListener {
            viewModel.setEvent(NewWordUiEvent.WordTyped(binding.editTextNewWord.text.toString()))
        }

        val clipBoardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        binding.imageViewPaste.setOnClickListener {
            val copiedString = clipBoardManager.primaryClip?.getItemAt(0)?.text?.toString()
            if (copiedString != null) {
                binding.editTextNewWord.setText(copiedString)
            }
        }

        binding.imageViewEdit.setOnClickListener {
            if (binding.editTextTranslationAre.isEnabled) {
                binding.editTextTranslationAre.isEnabled = false
                binding.imageViewEdit.setImageDrawable(
                    getDrawable(
                        requireContext(),
                        R.drawable.ic_edit
                    )
                )
            } else {
                binding.editTextTranslationAre.isEnabled = true
                binding.imageViewEdit.setImageDrawable(
                    getDrawable(
                        requireContext(),
                        R.drawable.ic_check
                    )
                )
            }


        }
        binding.spinnerCategory.onItemSelectedListener = this

    }


    private fun onHandleState(state: NewWordUiState) {
        val nativeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_langs_dropdown,
            viewModel.uiState.value.nativeLanguage.map { it.emojiCode })


        studiedAdapter.clear()
        studiedAdapter.addAll(viewModel.uiState.value.studiedLanguages.map { it.emojiCode })
        studiedAdapter.notifyDataSetChanged()

        binding.spinnerLanguage.adapter = nativeAdapter

        categoriesAdapter =
            CategoriesDropDownAdapter(requireContext(), viewModel.uiState.value.categories)

        binding.spinnerCategory.adapter = categoriesAdapter
        binding.spinnerCategory.setSelection(state.selectedCategory)
        binding.editTextTranslationAre.setText(state.translation)
        if (state.word != binding.editTextNewWord.text.toString()) {
            binding.editTextNewWord.setText(state.word)
        }

        if (state.isError) {
            binding.editTextTranslationAre.error = "Oops, something went wrong"
            binding.editTextTranslationAre.hint = "enter word manually"
        } else {
            binding.editTextTranslationAre.error = null
            binding.editTextTranslationAre.hint = getString(R.string.translation_are)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.spinnerLanguageTo.setSelection(0, false)
        binding.spinnerLanguageTo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    viewModel.setEvent(NewWordUiEvent.LanguageSelected(viewModel.uiState.value.studiedLanguages[i].code))
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {

                }
            }
        binding.spinnerLanguageTo.setSelection(0, false)

    }

    private fun onHandleEffect(effect: NewWordUiSideEffect) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.setEvent(NewWordUiEvent.CategorySelected(categoriesAdapter.getItem(position).id))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //do nothing
    }
}