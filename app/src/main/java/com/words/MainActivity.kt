package com.words

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.words.databinding.ActivityMainBinding
import com.words.presentation.targets.view.TargetsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        binding.bottomNavigationView.setupWithNavController(findNavController(R.id.fragmentContainerView))

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val isNativeSeleted = sharedPref.getBoolean(getString(R.string.isNativeSelect), false)

        if (!isNativeSeleted) {
            TargetsFragment().show(
                supportFragmentManager, ""
            )
        }

    }

}