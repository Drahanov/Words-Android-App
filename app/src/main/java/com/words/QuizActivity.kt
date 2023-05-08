package com.words

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.words.databinding.ActivityQuizBinding
import com.words.presentation.question.view.QuestionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        val fragment = binding.questionFragment.getFragment<QuestionFragment>()

        val isEnabled = intent.getBooleanExtra("includeLearnedKey", false)
        val amount = intent.getIntExtra("wordsAmountKey", 0)

        val bundle = Bundle()

        bundle.putBoolean("includeLearnedKey", isEnabled)
        bundle.putInt("wordsAmountKey", amount)

        fragment.arguments = bundle
    }
}