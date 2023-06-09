package id.furqoncreative.jetstories.ui.pages.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.ui.pages.login.components.LoginHeader
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    LoginHeader(modifier = modifier)
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun LoginScreenPreview() {
    JetStoriesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LoginScreen()
        }
    }
}