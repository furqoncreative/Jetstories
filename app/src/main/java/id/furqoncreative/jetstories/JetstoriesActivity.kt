package id.furqoncreative.jetstories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import id.furqoncreative.jetstories.ui.JetstoriesApp
import id.furqoncreative.jetstories.ui.JetstoriesViewModel
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme

@AndroidEntryPoint
class JetstoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: JetstoriesViewModel by viewModels()
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{viewModel.isLoading.value}

        setContent {
            JetStoriesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    JetstoriesApp()
                }
            }
        }
    }
}