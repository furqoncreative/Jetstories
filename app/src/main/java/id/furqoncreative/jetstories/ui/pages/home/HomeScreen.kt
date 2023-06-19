package id.furqoncreative.jetstories.ui.pages.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import id.furqoncreative.jetstories.ui.pages.login.components.LoginHeader

@Composable
fun HomeScreen(
    onClickDetail: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    uiState.userMessage?.let { userMessage ->
        val snackbarText = stringResource(userMessage)
        LaunchedEffect(snackbarHostState, homeViewModel, userMessage, snackbarText) {
            snackbarHostState.showSnackbar(snackbarText)
            homeViewModel.snackbarMessageShown()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        if (uiState.isLoading) {
            LinearProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
        } else {
            Box(modifier = modifier) {
                val stories = uiState.stories
                if (stories.isNullOrEmpty()) {
                    Text(text = "Nothing")
                } else {
                    LazyColumn {
                        items(stories, key = { it.id }) { story ->
                            AsyncImage(
                                model = story.photoUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 200.dp)
                            )
                        }
                    }
                }
            }
        }

    }

    SnackbarHost(hostState = snackbarHostState, Modifier)
}