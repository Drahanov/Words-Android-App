package com.words.presentation.categories.view

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.words.R
import com.words.databinding.FragmentCategoriesBinding
import com.words.domain.category.model.CategoryEntity
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.categories.model.CategoriesModel.*
import com.words.presentation.categories.view.adapters.ColorsDropDownAdapter
import com.words.presentation.categories.viewmodel.CategoriesViewModel
import com.words.presentation.home.model.HomeModel
import com.words.presentation.newWord.view.adapters.CategoriesDropDownAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@AndroidEntryPoint
class CategoriesFragment : DialogFragment() {

    private val viewModel: CategoriesViewModel by viewModels()
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var colorsAdapter: ColorsDropDownAdapter

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.rvCategories.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecyclerViewCategories()
            }
        }

        binding.buttonAddNewCategory.setOnClickListener {
            showEditDialog()
        }

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        return view
    }

    private fun onHandleState(state: CategoriesUiState) {
        binding.rvCategories.setContent {
            RecyclerViewCategories()
        }
    }

    private fun showEditDialog(
        category: CategoryEntity? = null
    ) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_new_category);
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        colorsAdapter =
            ColorsDropDownAdapter(
                requireContext(),
                viewModel.uiState.value.colors
            )

        val spinner = dialog.findViewById<Spinner>(R.id.spinnerColor)
        spinner.adapter = colorsAdapter
        if (category != null) {
            spinner.setSelection(viewModel.uiState.value.colors.indexOf(category.colorHex))
            dialog.findViewById<EditText>(R.id.editTextCategoryName).setText(category.title)
        }

        dialog.findViewById<ImageButton>(R.id.buttonCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<ImageButton>(R.id.buttonConfirmNewCategory).setOnClickListener {
            val categoryTitle = dialog.findViewById<EditText>(R.id.editTextCategoryName).text
            val categoryColor = colorsAdapter.getItem(spinner.selectedItemPosition)
            Toast.makeText(
                requireContext(),
                spinner.selectedItemPosition.toString(),
                Toast.LENGTH_SHORT
            ).show()
            Toast.makeText(requireContext(), categoryColor.toString(), Toast.LENGTH_SHORT).show()
            if (category != null) {
                viewModel.setEvent(
                    CategoriesUiEvent.UpdateCategory(
                        CategoryEntity(
                            id = category.id,
                            title = categoryTitle.toString(),
                            colorHex = categoryColor
                        )
                    )
                )
            } else {
                viewModel.setEvent(
                    CategoriesUiEvent.AddNewCategory(
                        CategoryEntity(
                            id = 0,
                            title = categoryTitle.toString(),
                            colorHex = categoryColor
                        )
                    )
                )
            }
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun CategoryItem(
        category: CategoryEntity,
        onSwipe: (id: CategoryEntity) -> Unit,
        onEditClick: (id: CategoryEntity) -> Unit
    ) {
        val delete = SwipeAction(
            icon = rememberVectorPainter(Icons.TwoTone.Delete),
            background = Color.Red,
            onSwipe = {
                onSwipe.invoke(category)
            }
        )

        val expanded = remember { mutableStateOf(false) }
        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false
        ) {
            Surface(
                color = Color.White,
                modifier = Modifier.padding(bottom = 5.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                SwipeableActionsBox(endActions = listOf(delete), swipeThreshold = 100.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Surface(
                            modifier = Modifier
                                .height(50.dp)
                                .width(13.dp),
                            color = Color(category.colorHex)
                        ) {}

                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = category.title,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Normal,
                                fontSize = 17.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(end = 5.dp, start = 5.dp),
                                textAlign = TextAlign.Center,
                            )
                            IconButton(
                                onClick = {
                                    onEditClick.invoke(category)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_edit),
                                    tint = Color.LightGray,
                                    contentDescription = null, // decorative element
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun RecyclerViewCategories(
        categories: List<CategoryEntity> = viewModel.uiState.value.categories,
    ) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = categories) { category ->
                CategoryItem(category = category, onSwipe = {
                    viewModel.setEvent(CategoriesUiEvent.DeleteNewCategory(it))
                }, onEditClick = {
                    showEditDialog(category = it)
                })
            }
        }
    }
}
