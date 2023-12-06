package id.furqoncreative.jetstories.ui.screens.register

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesCircularProgressBar
import id.furqoncreative.jetstories.ui.components.JetstoriesEmailTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesHeader
import id.furqoncreative.jetstories.ui.components.JetstoriesNameTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesPasswordTextField
import id.furqoncreative.jetstories.ui.components.TitleToolbar
import id.furqoncreative.jetstories.ui.components.states.ConfirmPasswordState
import id.furqoncreative.jetstories.ui.components.states.NameState
import id.furqoncreative.jetstories.ui.components.states.PasswordState
import id.furqoncreative.jetstories.ui.components.states.TextFieldState
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.utils.showToast
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel,
    onNavUp: () -> Unit,
    onSuccessRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()
    val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val context = LocalContext.current
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
        context.showToast(message = userMessage.asString(context))
        registerViewModel.toastMessageShown()
    }

    JetstoriesHeader(
        modifier = modifier,
        state = collapsingToolbarScaffoldState,
        startToolbarContent = {
            IconButton(onClick = {
                onNavUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = stringResource(
                        id = R.string.back
                    )
                )
            }
        },
        titleToolbarContent = {
            TitleToolbar(
                modifier = Modifier.padding(
                    top = 10.dp, start = 40.dp, bottom = 16.dp, end = 40.dp
                ),
                title = stringResource(id = R.string.register),
                textSize = it
            )
        }
    ) {
        RegisterContent(
            context = context,
            emailState = uiState.emailState,
            nameState = uiState.nameState,
            passwordState = uiState.passwordState,
            confirmPasswordState = uiState.confirmPasswordState,
            isLoading = uiState.isLoading,
            onClickSignup = onClickRegister
        )
    }
}

@Composable
fun RegisterContent(
    context: Context,
    emailState: TextFieldState,
    nameState: NameState,
    passwordState: PasswordState,
    confirmPasswordState: ConfirmPasswordState,
    onClickSignup: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val commonModifier = modifier.fillMaxWidth()

    Column(
        modifier = commonModifier
            .fillMaxHeight()
            .verticalScroll(state = rememberScrollState())
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        JetstoriesEmailTextField(
            modifier = commonModifier, context = context, emailState = emailState
        )

        JetstoriesNameTextField(modifier = commonModifier, context = context, nameState = nameState)

        JetstoriesPasswordTextField(
            modifier = commonModifier,
            context = context,
            label = stringResource(id = R.string.password_label),
            placeholder = stringResource(id = R.string.password_placeholder),
            passwordState = passwordState,
            imeAction = ImeAction.Next
        )

        JetstoriesPasswordTextField(modifier = commonModifier,
            context = context,
            label = stringResource(id = R.string.confirmation_password_label),
            placeholder = stringResource(id = R.string.confirm_password_placeholder),
            passwordState = confirmPasswordState,
            onImeAction = {
                onClickSignup()
            })

        Button(
            modifier = commonModifier.height(56.dp),
            enabled = emailState.isValid && nameState.isValid && passwordState.isValid && confirmPasswordState.isValid && !isLoading,
            onClick = {
                onClickSignup()
            }
        ) {
            if (!isLoading) {
                Text(text = stringResource(R.string.sign_up))
            } else {
                JetstoriesCircularProgressBar()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterBodyPreview() {
    JetStoriesTheme {
        RegisterContent(
            context = LocalContext.current,
            emailState = TextFieldState(),
            nameState = NameState(),
            passwordState = PasswordState(),
            confirmPasswordState = ConfirmPasswordState(PasswordState()),
            onClickSignup = {}
        )
    }
}