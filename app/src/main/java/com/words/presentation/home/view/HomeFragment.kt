package com.words.presentation.home.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.words.R
import com.words.databinding.FragmentHomeBinding
import com.words.domain.category.model.CategoryEntity
import com.words.domain.languages.model.LanguageEntity
import com.words.domain.words.model.WordEntity
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.home.model.HomeModel.*
import com.words.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.time.format.TextStyle

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

        viewModel.sideEffect
            .onEach(::onHandleEffect)
            .observeIn(this)

        initRecyclerView()

        return view
    }

    private fun isTargetsSelected(): Boolean {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return false
        val highScore = sharedPref.getBoolean(getString(R.string.isTargetsSelect), false)
        return highScore
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
                        text = languageModel.emojiCode,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(6.dp),
                    )
                    Text(
                        text = languageModel.emojiCode,
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
    private fun WordItem(
        word: WordEntity,
        color: Long?,
        onSwipe: (id: WordEntity) -> Unit,
        onCheck: (id: WordEntity, isChecked: Boolean) -> Unit
    ) {
        val delete = SwipeAction(
            icon = rememberVectorPainter(Icons.TwoTone.Delete),
            background = Color.Red,
            onSwipe = {
                onSwipe.invoke(word)
            }
        )
        val checkedState = remember {
            mutableStateOf(word.isLearned)
        }
        val expanded = remember { mutableStateOf(false) }
        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false
        ) {
            Surface(
                color = if (checkedState.value) Color(0xFFEBF0FF) else Color(0xFFDEE5FF),
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
                                style = if (checkedState.value) androidx.compose.ui.text.TextStyle(
                                    textDecoration = TextDecoration.LineThrough
                                ) else androidx.compose.ui.text.TextStyle(textDecoration = TextDecoration.None),
                                text = word.word,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                style = if (checkedState.value) androidx.compose.ui.text.TextStyle(
                                    textDecoration = TextDecoration.LineThrough
                                ) else androidx.compose.ui.text.TextStyle(textDecoration = TextDecoration.None),
                                text = word.translation,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color(0xff787676),
                                modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                                textAlign = TextAlign.Center,
                            )

                        }

                        Text(
                            style = if (checkedState.value) androidx.compose.ui.text.TextStyle(
                                textDecoration = TextDecoration.LineThrough
                            ) else androidx.compose.ui.text.TextStyle(textDecoration = TextDecoration.None),
                            text = word.langEmoji,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(10.dp)
                                .weight(.2f),
                            textAlign = TextAlign.Center,
                        )

                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                onCheck.invoke(word, it)
                            },
                            Modifier
                                .padding(10.dp)
                                .weight(.2f),
                            colors = checkBoxColors()
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun checkBoxColors(): CheckboxColors {
        return CheckboxDefaults.colors(
            checkedColor = Color(0xFF6078F3),
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun CategoryItem(
        category: CategoryEntity,
        isSelected: Boolean,
        onSelected: (id: Int) -> Unit
    ) {

        val expanded = remember { mutableStateOf(isSelected) }

        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false
        ) {
            Surface(
                color = if (!expanded.value) Color(0xFFDEE5FF) else Color(0xFF6078F3),
                modifier = Modifier.padding(end = 3.dp),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    expanded.value = !expanded.value
                    onSelected.invoke(category.id)
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
        categories: List<CategoryEntity> = viewModel.uiState.value.categories,
        selected: List<Int> = viewModel.uiState.value.selectedCategories
    ) {
        LazyRow(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = categories) { category ->
                CategoryItem(category, selected.contains(category.id)) {
                    viewModel.setEvent(HomeUiEvent.ClickCategory(it))
                }
            }
        }
    }

    @Composable
    private fun RecyclerViewWords(
        words: List<WordEntity> = viewModel.uiState.value.words,
        categories: List<CategoryEntity> = viewModel.uiState.value.categories,
        searchKeyword: String = viewModel.uiState.value.searchKeyword
    ) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = words) { word ->
                if (word.word.contains(searchKeyword) || searchKeyword.isEmpty() || searchKeyword.isBlank()) {
                    WordItem(word = word, color = categories.find {
                        it.id == word.categoryId
                    }!!.colorHex,
                        onCheck = { word, isChecked ->
                            viewModel.setEvent(HomeUiEvent.WordLearned(word, isChecked))
                        },
                        onSwipe = {
                            viewModel.setEvent(HomeUiEvent.RemoveWord(it))
                        })
                }
            }
        }
    }

    @Composable
    private fun RecyclerView(
        names: List<LanguageEntity> = listOf(
//            LanguageEntity(
//                id=0,
//                title = "i",
//                "all",
//                "\uD83C\uDFF3️\u200D\uD83C\uDF08"
//            ),
//            LanguageEntity(
//                "",
//                "ukrainian",
//                "\uD83C\uDDFA\uD83C\uDDE6"
//            ),
//            LanguageEntity(
//                "",
//                "polish",
//                "\uD83C\uDDF5\uD83C\uDDF1"
//            ),
//            LanguageEntity(
//                "",
//                "english",
//                "\uD83C\uDDEC\uD83C\uDDE7"
//            ),
//            LanguageEntity(
//                "",
//                "bulgarian",
//                "\uD83C\uDDE7\uD83C\uDDEC"
//            )
        )
    ) {
        LazyRow(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                LanguageItem(name)
            }
        }
    }

    private fun onHandleState(state: HomeUiState) {
        if (state.words.isEmpty()) {
            binding.imageNoData.visibility = View.VISIBLE
        } else {
            binding.imageNoData.visibility = View.GONE
        }
        binding.rvCategories.setContent {
            RecyclerViewCategories()
        }
        binding.rvWords.setContent {
            RecyclerViewWords()
        }
    }

    private fun onHandleEffect(effect: HomeUiSideEffect) {

    }
}