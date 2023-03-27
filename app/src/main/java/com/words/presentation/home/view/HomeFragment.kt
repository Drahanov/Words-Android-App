package com.words.presentation.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.words.R
import com.words.databinding.FragmentHomeBinding
import com.words.domain.category.model.CategoryEntity
import com.words.domain.languages.models.LanguageEntity
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.home.model.HomeModel.*
import com.words.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        viewModel.sideEffect
            .onEach(::onHandleEffect)
            .observeIn(this)

        initRecyclerView()
        initListeners()

        return view
    }

    private fun initListeners() {
        binding.buttonNewWord.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(
                R.id.homeFragmentToNewWordDialog
            )
        }
    }

    private fun initRecyclerView() {
        binding.rvLanguages.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerView()
            }
        }
        binding.rvCategories.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerViewCategories()
            }
        }
        binding.rvWords.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerViewWords()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun LanguageItem(languageModel: LanguageEntity) {

        val expanded = remember { mutableStateOf(false) }

        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false
        ) {
            Surface(
                color = Color(0xFFE7EAFA),
                modifier = Modifier
                    .padding(end = 3.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                Row(
                    modifier = Modifier.border(
                        width = if (expanded.value) 2.dp else 0.dp,
                        Color(0xFF6078F3),
                        shape = RoundedCornerShape(5.dp)
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = languageModel.languageEmojiCode,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(6.dp),
                    )
                    Text(

                        text = languageModel.languageName,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = Color(0xFF787676),
                        modifier = Modifier.padding(end = 10.dp),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun WordItem() {

        val expanded = remember { mutableStateOf(false) }
        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false
        ) {
            Surface(
                color = Color(0xFFf4f9fc),
                modifier = Modifier.padding(bottom = 5.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Surface(
                        modifier = Modifier
                            .height(70.dp)
                            .width(10.dp),
                        color = Color.Blue
                    ) {}

                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {

                        Text(
                            text = "excellent",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = "неймовірно",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xff787676),
                            modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                            textAlign = TextAlign.Center,
                        )

                    }
                }

            }
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun CategoryItem(category: CategoryEntity) {

        val expanded = remember { mutableStateOf(false) }

        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false
        ) {
            Surface(
                color = if (!expanded.value) Color(0xffe1eefe) else Color(0xFF6078F3),
                modifier = Modifier.padding(end = 3.dp),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .clip(CircleShape)
                            .height(10.dp)
                            .width(10.dp),
                        color = Color(category.colorHex)
                    ) {

                    }
                    Text(
                        text = category.title,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = if (!expanded.value) Color(0xFF787676) else Color.White,
                        modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                        textAlign = TextAlign.Center,
                    )

                }

            }
        }
    }

    @Composable
    private fun RecyclerViewCategories(
        names: List<CategoryEntity> = listOf(
            CategoryEntity("", title = "All", colorHex = 0xFF0000FF),
            CategoryEntity("", title = "Professions", colorHex = 0xFF00FFFF),
            CategoryEntity("", title = "Default", colorHex = 0xFFFFFF00),
            CategoryEntity("", title = "Foods", colorHex = 0xFFFF00FF),
            CategoryEntity("", title = "Emotions", colorHex = 0xFFFF0000),
        )
    ) {
        LazyRow(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                CategoryItem(name)
            }
        }
    }

    @Composable
    private fun RecyclerViewWords(
        names: List<String> = listOf(
            "", "", "", "", ""
        )
    ) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                WordItem()
            }
        }
    }

    @Composable
    private fun RecyclerView(
        names: List<LanguageEntity> = listOf(
            LanguageEntity(
                "",
                "all",
                "\uD83C\uDFF3️\u200D\uD83C\uDF08"
            ),
            LanguageEntity(
                "",
                "ukrainian",
                "\uD83C\uDDFA\uD83C\uDDE6"
            ),
            LanguageEntity(
                "",
                "polish",
                "\uD83C\uDDF5\uD83C\uDDF1"
            ),
            LanguageEntity(
                "",
                "english",
                "\uD83C\uDDEC\uD83C\uDDE7"
            ),
            LanguageEntity(
                "",
                "bulgarian",
                "\uD83C\uDDE7\uD83C\uDDEC"
            )
        )
    ) {
        LazyRow(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                LanguageItem(name)
            }
        }
    }

    private fun onHandleState(state: HomeUiState) {
        Toast.makeText(context, state.words.size.toString(), Toast.LENGTH_LONG).show()
    }

    private fun onHandleEffect(effect: HomeUiSideEffect) {

    }
}