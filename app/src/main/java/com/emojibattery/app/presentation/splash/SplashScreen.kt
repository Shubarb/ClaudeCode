package com.emojibattery.app.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.emojibattery.app.R
import com.emojibattery.app.core.mvi.CollectEffect
import com.emojibattery.app.presentation.theme.BrandPurple
import com.emojibattery.app.presentation.theme.BrandPurpleDark
import com.emojibattery.app.presentation.theme.BrandYellow
import com.emojibattery.app.presentation.theme.EmojiBatteryTheme
import com.emojibattery.app.presentation.theme.White
import com.emojibattery.app.presentation.theme.WhiteAlpha20
import com.emojibattery.app.presentation.theme.WhiteAlpha70

/** Stateful entry: wires the ViewModel to the stateless [SplashScreen]. */
@Composable
fun SplashRoute(
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.onIntent(SplashIntent.Start) }

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            SplashEffect.NavigateToHome -> onNavigateToHome()
        }
    }

    SplashScreen(state = state)
}

@Composable
fun SplashScreen(state: SplashState, modifier: Modifier = Modifier) {
    val progress by animateFloatAsState(targetValue = state.progress, label = "splashProgress")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BrandPurple, BrandPurpleDark)))
            .systemBarsPadding(),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(WhiteAlpha20),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(140.dp),
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.app_name),
                color = White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.splash_tagline),
                color = WhiteAlpha70,
                fontSize = 15.sp,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 48.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = BrandYellow,
                trackColor = WhiteAlpha20,
                strokeCap = StrokeCap.Round,
                gapSize = 0.dp,
                drawStopIndicator = {},
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                color = White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun SplashScreenPreview() {
    EmojiBatteryTheme {
        SplashScreen(state = SplashState(progress = 0.6f))
    }
}
