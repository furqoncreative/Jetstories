package id.furqoncreative.jetstories.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.ui.components.JetstoriesAlertDialog
import id.furqoncreative.jetstories.ui.components.JetstoriesHeader
import id.furqoncreative.jetstories.ui.components.JetstoriesIconButton
import id.furqoncreative.jetstories.ui.components.JetstoriesLinearProgressBar
import id.furqoncreative.jetstories.ui.components.JetstoriesOptionMenu
import id.furqoncreative.jetstories.ui.components.JetstoriesSearchBar
import id.furqoncreative.jetstories.ui.components.MenuItem
import id.furqoncreative.jetstories.ui.components.TitleToolbar
import id.furqoncreative.jetstories.utils.showToast
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun HomeScreen(
    onNavigateToAddStory: () -> Unit,
    onNavigateToMapView: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToFavorite: () -> Unit,
    onNavigateToDetail: (Story?) -> Unit,
    onUserLoggedOut: () -> Unit,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    val optionMenuExpandState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val alertDialogState: MutableState<Boolean> = remember { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()
    val storiesLazyPagingItems = uiState.stories.collectAsLazyPagingItems()

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
                textAlign = TextAlign.Center,
            )
        })

    JetstoriesHeader(modifier = modifier,
        state = collapsingToolbarScaffoldState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        titleToolbarContent = {
            TitleToolbar(
                modifier = Modifier.padding(
                    top = 10.dp, start = 16.dp, bottom = 16.dp
                ), title = stringResource(id = R.string.app_name), textSize = it
            )
        },
        endToolbarContent = {
            JetstoriesIconButton(
                icon = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_story)
            ) {
                onNavigateToAddStory()
            }

            JetstoriesIconButton(
                icon = Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.favorite_stories)
            ) {
                onNavigateToFavorite()
            }

            JetstoriesIconButton(
                icon = Icons.Default.Map,
                contentDescription = stringResource(id = R.string.map_view)
            ) {
                onNavigateToMapView()
            }

            JetstoriesIconButton(
                icon = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.more_menu)
            ) {
                optionMenuExpandState.value = true
            }

            JetstoriesOptionMenu(
                context = context,
                expanded = optionMenuExpandState,
                onClickMenu = mapOf(
                    Pair(MenuItem.LOGOUT) {
                        alertDialogState.value = true
                    },
                    Pair(MenuItem.SETTINGS) {
                        onNavigateToSettings()
                    },
                    Pair(MenuItem.ABOUT) {
                        onNavigateToAbout()
                    },
                )
            )
        }) {

        HomeContent(
            isLoading = uiState.isLoading,
            storiesLazyPagingItems = storiesLazyPagingItems,
            lazyListState = lazyListState,
            query = uiState.searchQuery,
            searchStory = {
                homeViewModel.searchStory(it)
            },
            onStoryClicked = { story ->
                onNavigateToDetail(story)
            }
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    storiesLazyPagingItems: LazyPagingItems<Story>,
    lazyListState: LazyListState,
    query: String,
    searchStory: (String) -> Unit,
    isLoading: Boolean,
    onStoryClicked: (Story?) -> Unit,
    modifier: Modifier = Modifier
) {
    val commonModifier = modifier.fillMaxWidth()

    if (isLoading) {
        JetstoriesLinearProgressBar()
    } else {
        Box(
            modifier = commonModifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = commonModifier.testTag("story_list"),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                stickyHeader {
                    JetstoriesSearchBar(
                        query = query,
                        onQueryChange = {
                            searchStory(it)
                        }, onSearch = {
                            searchStory(it)
                        }
                    )
                }

                items(
                    key = storiesLazyPagingItems.itemKey { it.id },
                    count = storiesLazyPagingItems.itemCount
                ) { index ->
                    val story = storiesLazyPagingItems[index]

                    StoryRow(story = story, onStoryClicked = { onStoryClicked(story) })
                }

                item {
                    if (storiesLazyPagingItems.itemCount == 0) {
                        Box(
                            modifier = commonModifier.padding(bottom = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(R.string.there_is_no_story))
                        }
                    }
                }

                storiesLazyPagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                JetstoriesLinearProgressBar()
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error =
                                storiesLazyPagingItems.loadState.refresh as LoadState.Error
                            item {
                                ErrorMessage(modifier = Modifier.fillParentMaxSize(),
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                JetstoriesLinearProgressBar()
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val error =
                                storiesLazyPagingItems.loadState.append as LoadState.Error
                            item {
                                ErrorMessage(modifier = Modifier,
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(
    message: String,
    onClickRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
            maxLines = 2
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(id = R.string.strRetry))
        }
    }
}