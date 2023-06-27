package id.furqoncreative.jetstories.ui.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.model.stories.Story

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    stories: List<Story>?,
    onClickStory: (Story) -> Unit
) {
    val commonModifier = modifier.fillMaxWidth()
    if (isLoading) {
        LinearProgressIndicator(
            modifier = commonModifier.padding(16.dp), color = MaterialTheme.colorScheme.tertiary
        )
    } else {
        if (stories.isNullOrEmpty()) {
            Box(
                modifier = commonModifier.padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.there_is_no_story))
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(stories, key = { it.id }) { story ->
                    StoryRow(story = story, onClickStory = { onClickStory(story) })
                }
            }
        }
    }
}