package com.words

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.words.databinding.ActivityMainBinding
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.newWord.model.NewWordModel
import com.words.presentation.newWord.view.NewWordFragment
import com.words.presentation.newWord.viewmodel.NewWordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            val bottomSheetDialog = NewWordFragment()
            bottomSheetDialog.show(supportFragmentManager, "TAG")
        }
    }
}