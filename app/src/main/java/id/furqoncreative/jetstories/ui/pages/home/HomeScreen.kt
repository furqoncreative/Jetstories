package id.furqoncreative.jetstories.ui.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.source.local.StoryItem
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.ui.components.JetstoriesAlertDialog
import id.furqoncreative.jetstories.ui.components.JetstoriesOptionMenu
import id.furqoncreative.jetstories.ui.components.MenuItem
import id.furqoncreative.jetstories.utils.showToast
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickAddStory: () -> Unit,
    onClickMapView: () -> Unit,
    onClickSettings: () -> Unit,
    onClickStory: (StoryItem) -> Unit,
    onUserLoggedOut: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val optionMenuExpandState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val alertDialogState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val collapsingToolbarScaffoldState: CollapsingToolbarScaffoldState =
        rememberCollapsingToolbarScaffoldState()
    val context = LocalContext.current

    uiState.userMessage?.let { userMessage ->
        context.showToast(userMessage.asString(context))
        homeViewModel.toastMessageShown()
    }

    LaunchedEffect(uiState.isUserLogout) {
        if (uiState.isUserLogout) {
            onUserLoggedOut()
        }
    }

    JetstoriesAlertDialog(openDialog = alertDialogState,
        title = stringResource(R.string.logout),
        confirmText = stringResource(R.string.yes),
        dismissText = stringResource(R.string.no),
        confirmAction = {
            homeViewModel.userLogout()
        },
        icon = {
            Icon(Icons.Filled.Logout, contentDescription = null)
        },
        content = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.logout_confirmation),
                textAlign = TextAlign.Center
            )
        })

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
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_story)
                    )
                }
                IconButton(onClick = { onClickMapView() }) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = stringResource(R.string.map_view)
                    )
                }
                IconButton(onClick = { optionMenuExpandState.value = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.more_menu)
                    )
                }
                JetstoriesOptionMenu(
                    context = context,
                    expanded = optionMenuExpandState,
                    onClickMenu = mapOf(Pair(first = MenuItem.LOGOUT, second = {
                        alertDialogState.value = true
                    }), Pair(MenuItem.SETTINGS) {
                        onClickSettings()
                    })
                )
            }

        }) {

        HomeContent(isLoading = uiState.isLoading,
            stories = uiState.stories,
            onClickStory = { story ->
                onClickStory(story)
            })
    }
}



