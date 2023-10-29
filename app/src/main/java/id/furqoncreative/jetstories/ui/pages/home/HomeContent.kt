package id.furqoncreative.jetstories.ui.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.data.source.local.StoryItem

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    stories: Pager<Int, StoryItem>?,
    onClickStory: (StoryItem) -> Unit
) {
    val commonModifier = modifier.fillMaxWidth()
    if (isLoading) {
        LinearProgressIndicator(
            modifier = commonModifier.padding(16.dp), color = MaterialTheme.colorScheme.tertiary
        )
    } else {
        if (stories == null) {
            Box(
                modifier = commonModifier.padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.there_is_no_story))
            }
        } else {
            val lazyPagingItems: LazyPagingItems<StoryItem> =
                stories.flow.collectAsLazyPagingItems()

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(lazyPagingItems.itemCount) { index ->
                    val story = lazyPagingItems[index]!!
                    StoryRow(story = story, onClickStory = { onClickStory(story) })
                }

                lazyPagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                LinearProgressIndicator(
                                    modifier = commonModifier.padding(16.dp),
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = lazyPagingItems.loadState.refresh as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier.fillParentMaxSize(),
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                LinearProgressIndicator(
                                    modifier = commonModifier.padding(16.dp),
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = lazyPagingItems.loadState.append as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier,
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
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
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