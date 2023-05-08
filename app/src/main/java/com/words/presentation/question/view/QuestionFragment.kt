package com.words.presentation.question.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.words.MainActivity
import com.words.databinding.FragmentQuestionBinding
import com.words.presentation.base.viewmodel.observeIn
import com.words.presentation.question.model.QuestionModel
import com.words.presentation.question.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private val viewModel: QuestionViewModel by viewModels()
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
    private lateinit var count: CountDownTimer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)

        Toast.makeText(
            context,
            arguments?.getInt("wordsAmountKey", 0).toString(),
            Toast.LENGTH_SHORT
        ).show()

        Toast.makeText(
            context,
            arguments?.getBoolean("includeLearnedKey", false).toString(),
            Toast.LENGTH_SHORT
        ).show()

        viewModel.uiState
            .onEach(::onHandleState)
            .observeIn(this)

        binding.button.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        viewModel.sideEffect
            .onEach(::onHandleEffect)
            .observeIn(this)

        binding.progressBar.setProgress(10)
        countdown()

        binding.frameLayout.setOnClickListener {
            startNewRound()
        }

        binding.frameLayout1.setOnClickListener {
            startNewRound()
        }

        binding.frameLayout2.setOnClickListener {
            startNewRound()
        }
        return binding.root
    }

    private fun onHandleEffect(effect: QuestionModel.QuestionUiSideEffect) {
        showResult()
    }

    private fun onHandleState(state: QuestionModel.QuestionUiState) {
        when (state.round) {
            1 -> {
                binding.word.text = state.words[0].translation
                binding.textViewQuestion.text = "Question 1/5"
                binding.answer1.text = "1. " + state.words[1].word
                binding.answer2.text = "2. " + state.words[0].word
                binding.answer3.text = "3. " + state.words[3].word
            }
            2 -> {
                binding.word.text = state.words[1].translation
                binding.textViewQuestion.text = "Question 2/5"
                binding.answer1.text = "1. " + state.words[1].word
                binding.answer2.text = "2. " + state.words[0].word
                binding.answer3.text = "3. " + state.words[3].word
            }
            3 -> {
                binding.word.text = state.words[2].translation
                binding.textViewQuestion.text = "Question 3/5"
                binding.answer1.text = "1. " + state.words[1].word
                binding.answer2.text = "2. " + state.words[2].word
                binding.answer3.text = "3. " + state.words[0].word
            }
            4 -> {
                binding.word.text = state.words[3].translation
                binding.textViewQuestion.text = "Question 4/5"
                binding.answer1.text = "1. " + state.words[3].word
                binding.answer2.text = "2. " + state.words[0].word
                binding.answer3.text = "3. " + state.words[1].word
            }
            5 -> {
                binding.word.text = state.words[4].translation
                binding.textViewQuestion.text = "Question 5/5"
                binding.answer1.text = "1. " + state.words[4].word
                binding.answer2.text = "2. " + state.words[3].word
                binding.answer3.text = "3. " + state.words[2].word
            }
        }

    }

    fun countdown() {
        var duration: Long = TimeUnit.SECONDS.toMillis(11)

        count = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val result = ((TimeUnit.SECONDS.toMillis(11) - millisUntilFinished) / 100).toInt()
                if (result > 1)
                    binding.progressBar.setProgress(result)
//                c ountDown.text = sDuration

            }

            override fun onFinish() {
//                index++
//                if (index<questionsList.size){
//                    questionModel=questionsList[index]
//                    setAllQuestions()
//                    resetBackground()
//                    enableButton()
//                    countdown()
//
//                }
//                else{
//
//                    gameResult()
//
//                }
                if (viewModel.uiState.value.round < 5)
                    startNewRound()

            }


        }.start()
    }

    private fun startNewRound() {
        count.cancel()
        countdown()
        binding.progressBar.progress = 10
        viewModel.setEvent(QuestionModel.QuestionUiEvent.Selected)
    }

    private fun showResult() {
        viewModel.setEvent(QuestionModel.QuestionUiEvent.SaveNewQuiz)
        binding.main.visibility = View.GONE
        binding.resultLayout.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}