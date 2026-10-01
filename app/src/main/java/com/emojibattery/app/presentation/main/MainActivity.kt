package com.emojibattery.app.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.emojibattery.app.core.base.BaseActivity
import com.emojibattery.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/** Single activity hosting all screens as Fragments via nav_graph.xml. */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }
}
