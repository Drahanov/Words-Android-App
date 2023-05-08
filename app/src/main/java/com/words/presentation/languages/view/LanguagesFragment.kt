package com.words.presentation.languages.view

import android.app.LocaleManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.words.R
import com.words.databinding.FragmentLanguagesBinding
import com.words.domain.languages.model.LanguageEntity
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.languages.model.LanguagesModel
import com.words.presentation.languages.viewmodel.LanguagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class LanguagesFragment : DialogFragment() {

    private val viewModel: LanguagesViewModel by viewModels()
    private var _binding: FragmentLanguagesBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentLanguagesBinding.inflate(inflater, container, false)


        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)


        binding.rvLanguages.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerViewLanguages()
            }
        }

        return binding.root
    }

    private fun onHandleState(state: LanguagesModel.LanguagesUiState) {
        binding.rvLanguages.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerViewLanguages()
            }
        }
    }

    @Composable
    private fun RecyclerViewLanguages(
        languages: List<LanguageEntity> = viewModel.uiState.value.languages,
        currentLang: String = viewModel.uiState.value.selectedLanguageCode
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val currentLang = sharedPref?.getString(getString(R.string.currentLanguage), "en")

            items(items = languages) { language ->
                com.words.presentation.targets.view.LanguageItem(
                    selected = (language.code.equals(currentLang)),
                    title = language.title,
                    onClick = {
                        with(sharedPref!!.edit()) {
                            putString(getString(R.string.currentLanguage), language.code)
                            apply()
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            context?.getSystemService(
                                LocaleManager::class.java
                            )
                                ?.setApplicationLocales(LocaleList(Locale.forLanguageTag(language.code)))
                        } else {
                            val appLocale: LocaleListCompat =
                                LocaleListCompat.forLanguageTags(language.code)
                            AppCompatDelegate.setApplicationLocales(appLocale)
                        }
                    }, emoji = language.emojiCode)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}