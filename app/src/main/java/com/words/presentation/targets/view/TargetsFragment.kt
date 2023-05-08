package com.words.presentation.targets.view

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.words.R
import com.words.databinding.FragmentTargetsBinding
import com.words.domain.languages.model.LanguageEntity
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.targets.model.Mode
import com.words.presentation.targets.model.TargetsModel
import com.words.presentation.targets.viewmodel.TargetsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TargetsFragment : DialogFragment() {

    private val viewModel: TargetsViewModel by viewModels()
    private var _binding: FragmentTargetsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.isCancelable = false
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTargetsBinding.inflate(inflater, container, false)

        binding.rvLanguages.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerViewLanguagesNative()
            }
        }

        viewModel.sideEffect
            .onEach(::onHandleEffect)
            .observeIn(this)

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        binding.buttonLanguagesSelect.setOnClickListener {
            viewModel.setEvent(TargetsModel.TargetsUiEvent.Confirmed)
        }

        return binding.root
    }

    private fun onHandleState(state: TargetsModel.TargetsUiState) {
        when (state.mode) {
            Mode.NATIVE -> {
                binding.rvLanguages.setContent {
                    RecyclerViewLanguagesNative()
                }
                binding.textViewHint.text = getString(R.string.hint_native_lang)
            }
            Mode.TARGETS -> {
                binding.rvLanguages.setContent {
                    RecyclerViewLanguagesTarget()
                }
                binding.textViewHint.text = getString(R.string.hint_target_langs)
            }
        }
    }

    private fun onHandleEffect(state: TargetsModel.TargetsUiEffect) {
        when (state) {
            TargetsModel.TargetsUiEffect.SelectItemUiEffect -> {
                Toast.makeText(context, "Select some language!", Toast.LENGTH_SHORT).show()
            }
            TargetsModel.TargetsUiEffect.LanguagesSelected -> {
                dismiss()
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()) {
                    putBoolean(getString(R.string.isNativeSelect), true)
                    apply()
                }
            }
        }
    }

    @Composable
    private fun RecyclerViewLanguagesNative(
        languages: List<LanguageEntity> = viewModel.uiState.value.languages
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            items(items = languages) { language ->
                LanguageItem(
                    selected = language.isNative,
                    title = language.title,
                    onClick = {
                        viewModel.setEvent(TargetsModel.TargetsUiEvent.LanguageSelected(language.id))
                    }, emoji = language.emojiCode
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    @Composable
    private fun RecyclerViewLanguagesTarget(
        languages: List<LanguageEntity> = viewModel.uiState.value.languages
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            items(items = languages) { language ->
                LanguageItem(
                    selected = language.isStudied,
                    title = language.title,
                    emoji = language.emojiCode,
                    onClick = {
                        viewModel.setEvent(TargetsModel.TargetsUiEvent.LanguageSelected(language.id))
                    })
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}