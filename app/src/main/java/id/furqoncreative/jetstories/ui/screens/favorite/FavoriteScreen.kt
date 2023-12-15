package id.furqoncreative.jetstories.ui.screens.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.ui.components.JetstoriesHeader
import id.furqoncreative.jetstories.ui.components.JetstoriesLinearProgressBar
import id.furqoncreative.jetstories.ui.components.TitleToolbar
import id.furqoncreative.jetstories.ui.screens.home.StoryRow
import id.furqoncreative.jetstories.utils.showToast
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@Composable
fun FavoriteScreen(
    favoriteViewModel: FavoriteViewModel,
    onNavUp: () -> Unit,
    onNavigateToDetail: (Story?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiState by favoriteViewModel.uiState.collectAsStateWithLifecycle()
    val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    val lazyListState = rememberLazyListState()

    uiState.userMessage?.let { userMessage ->
        context.showToast(userMessage.asString(context))
        favoriteViewModel.toastMessageShown()
    }

    LaunchedEffect(Unit) {
        favoriteViewModel.getAllFavoriteStories()
    }

    JetstoriesHeader(
        modifier = modifier,
        state = collapsingToolbarScaffoldState,
        titleToolbarContent = { textSize ->
            TitleToolbar(
                modifier = Modifier
                    .padding(
                        top = 10.dp, start = 40.dp, bottom = 16.dp, end = 40.dp
                    ),
                title = stringResource(R.string.favorite_stories),
                textSize = textSize
            )
        },
        startToolbarContent = {
            IconButton(
                onClick = {
                    onNavUp()
                }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    ) {
        FavoriteContent(
            isLoading = uiState.isLoading,
            stories = uiState.stories,
            lazyListState = lazyListState,
            onStoryClicked = {
                onNavigateToDetail(it)
            }
        )
    }
}

@Composable
fun FavoriteContent(
    isLoading: Boolean,
    stories: List<Story>?,
    lazyListState: LazyListState,
    onStoryClicked: (Story?) -> Unit,
    modifier: Modifier = Modifier
) {
    val commonModifier = modifier
        .fillMaxWidth()
        .fillMaxHeight()

    if (isLoading) {
        JetstoriesLinearProgressBar()
    } else {
        Box(
            modifier = commonModifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (stories.isNullOrEmpty()) {
                Box(
                    modifier = commonModifier.padding(bottom = 16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(text = stringResource(R.string.there_is_no_story))
                }
            } else {
                LazyColumn(
                    state = lazyListState,
                    modifier = commonModifier.testTag("favorite_story_list"),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = stories,
                        key = { it.id },
                    ) { story ->
                        StoryRow(story = story, onStoryClicked = { onStoryClicked(story) })
                    }
                }
            }

        }
    }

}
