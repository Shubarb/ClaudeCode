package com.emojibattery.app.presentation.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emojibattery.app.R
import com.emojibattery.app.core.base.BaseFragment
import com.emojibattery.app.core.extension.collectWhenStarted
import com.emojibattery.app.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun initView() {
        viewModel.onIntent(SplashIntent.Start)
    }

    override fun observe() {
        collectWhenStarted(viewModel.state, ::render)
        collectWhenStarted(viewModel.effect) { effect ->
            when (effect) {
                SplashEffect.NavigateToHome ->
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }
    }

    private fun render(state: SplashState) = with(binding) {
        progressLoading.setProgressCompat(state.progress, true)
        tvProgress.text = getString(R.string.splash_progress, state.progress)
    }
}
