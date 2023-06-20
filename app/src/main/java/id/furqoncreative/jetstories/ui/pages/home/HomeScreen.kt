package id.furqoncreative.jetstories.ui.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.ui.components.JetstoriesAlertDialog
import id.furqoncreative.jetstories.ui.components.MenuItem
import id.furqoncreative.jetstories.ui.components.OptionMenu
import id.furqoncreative.jetstories.ui.pages.home.components.StoryRow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import timber.log.Timber

@Composable
fun HomeScreen(
    onClickStoryItem: () -> Unit,
    onClickAddStory: () -> Unit,
    onClickSettings: () -> Unit,
    onUserLoggedOut: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    collapsingToolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    optionMenuExpandState: MutableState<Boolean> = remember { mutableStateOf(false) },
    alertDialogState: MutableState<Boolean> = remember { mutableStateOf(false) },
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

    LaunchedEffect(uiState.isUserLogout) {
        Timber.d("${uiState.isUserLogout}")
        if (uiState.isUserLogout) {
            onUserLoggedOut()
        }
    }

    JetstoriesAlertDialog(
        openDialog = alertDialogState,
        title = "Logout",
        text = "Are you sure you want to logout?",
        confirmText = "Yes",
        dismissText = "No",
        confirmAction = {
            homeViewModel.userLogout()
        },
    )

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
                "Jetstories", style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = textSize,
                    fontWeight = FontWeight.Medium
                ), modifier = Modifier
                    .padding(
                        top = 10.dp, start = 16.dp, bottom = 16.dp
                    )
                    .road(whenCollapsed = Alignment.TopStart, whenExpanded = Alignment.Center)
            )

            Row(
                modifier = Modifier.road(
                    whenExpanded = Alignment.BottomEnd, whenCollapsed = Alignment.TopEnd
                )
            ) {
                IconButton(onClick = { onClickAddStory() }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Story")
                }
                IconButton(onClick = { optionMenuExpandState.value = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Menu")
                }
                OptionMenu(
                    expanded = optionMenuExpandState,
                    onClickMenu = mapOf(Pair(first = MenuItem.LOGOUT, second = {
                        alertDialogState.value = true
                    }), Pair(MenuItem.SETTINGS) {
                        onClickSettings()
                    })
                )
            }

        }) {

        if (uiState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.tertiary
            )
        } else {
            val stories = uiState.stories
            if (stories.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = "Nothing")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(stories, key = { it.id }) { story ->
                        StoryRow(story = story, onClickStory = onClickStoryItem)
                    }
                }
            }
        }
    }

    SnackbarHost(hostState = snackbarHostState, Modifier)
}



