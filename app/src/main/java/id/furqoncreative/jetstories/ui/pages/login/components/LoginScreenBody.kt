package id.furqoncreative.jetstories.ui.pages.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.ui.components.EmailState
import id.furqoncreative.jetstories.ui.components.EmailStateSaver
import id.furqoncreative.jetstories.ui.components.EmailTextField
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBody(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
            mutableStateOf(EmailState())
        }

        EmailTextField(emailState = emailState, onImeAction = { })

        OutlinedTextField(value = "", label = {
            Text(text = "Password")
        }, placeholder = {
            Text(text = "Masukan password Anda")
        }, onValueChange = {

        }, modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = "Don't have an account?"
            )
            Text(
                text = "Sign up",
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Button(modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(), onClick = { /*TODO*/ }) {
            Text(text = "Sign in")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginBodyPreview() {
    JetStoriesTheme {
        LoginBody()
    }
}