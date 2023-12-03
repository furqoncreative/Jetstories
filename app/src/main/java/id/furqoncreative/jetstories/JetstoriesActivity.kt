package id.furqoncreative.jetstories

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import id.furqoncreative.jetstories.ui.JetstoriesApp
import id.furqoncreative.jetstories.ui.JetstoriesViewModel
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme


@AndroidEntryPoint
class JetstoriesActivity : AppCompatActivity() {
    private val viewModel: JetstoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(viewModel.getUpdateAppLanguage())
        )
        setContent {
            JetStoriesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<JetstoriesViewModel>()
                    JetstoriesApp(viewModel)
                }
            }
        }
    }
}