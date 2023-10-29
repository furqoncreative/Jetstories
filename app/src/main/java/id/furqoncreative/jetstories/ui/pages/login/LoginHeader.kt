package id.furqoncreative.jetstories.ui.pages.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.R
import kotlinx.coroutines.delay

@Composable
fun LoginHeader(
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .height(300.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LaunchedEffect(key1 = visible) {
                delay(500)
                visible = true
            }

            AnimatedVisibility(
                visible = visible, enter = slideInVertically(
                    animationSpec = tween(1500)
                ) + fadeIn(initialAlpha = 0.3f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jetstories_logo),
                    contentDescription = "Jetstories Logo",
                    modifier = Modifier.size(125.dp)
                )
            }

            AnimatedVisibility(
                visible = visible, enter = scaleIn(
                    initialScale = 0.3f, animationSpec = tween(1500)
                ) + fadeIn(initialAlpha = 0.3f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    Text(
                        text = stringResource(id = R.string.app_tagline),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginHeaderPreview() {
    LoginHeader()
}