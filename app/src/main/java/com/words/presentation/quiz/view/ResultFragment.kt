package com.words.presentation.quiz.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.words.MainActivity
import com.words.QuizActivity
import com.words.R
import com.words.databinding.FragmentQuestionBinding
import com.words.databinding.FragmentResultBinding

class ResultFragment : DialogFragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return binding.root
    }
}