package id.furqoncreative.jetstories.ui.pages.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.util.showToast
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    onNavUp: () -> Unit,
    onSuccessRegister: () -> Unit,
    modifier: Modifier = Modifier,
    collapsingToolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current

    val onClickRegister = {
        if (uiState.emailState.isValid && uiState.passwordState.isValid && uiState.confirmPasswordState.isValid) {
            registerViewModel.registerUser()
            keyboardController?.hide()
        }
    }

    LaunchedEffect(uiState.isSuccessRegister) {
        if (uiState.isSuccessRegister) {
            onSuccessRegister()
        }
    }

    uiState.userMessage?.let { userMessage ->
        LocalContext.current.showToast(message = userMessage)
        registerViewModel.toastMessageShown()
    }

    CollapsingToolbarScaffold(modifier = modifier,
        state = collapsingToolbarScaffoldState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val textSize =
                (20 + (30 - 12) * collapsingToolbarScaffoldState.toolbarState.progress).sp

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .pin()
                    .background(color = MaterialTheme.colorScheme.background)
            )
            Text(
                "Register", style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = textSize,
                    fontWeight = FontWeight.Medium
                ), modifier = Modifier
                    .padding(
                        top = 10.dp, start = 40.dp, bottom = 16.dp, end = 40.dp
                    )
                    .road(whenCollapsed = Alignment.TopStart, whenExpanded = Alignment.Center)
            )

            Row(
                modifier = Modifier.road(
                    whenExpanded = Alignment.BottomStart, whenCollapsed = Alignment.TopStart
                )
            ) {
                IconButton(onClick = {
                    onNavUp()
                }) {
                    Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "Back")
                }
            }

        }) {

        RegisterBody(
            emailState = uiState.emailState,
            nameState = uiState.nameState,
            passwordState = uiState.passwordState,
            confirmPasswordState = uiState.confirmPasswordState,
            isLoading = uiState.isLoading,
            onClickSignup = onClickRegister
        )
    }
}