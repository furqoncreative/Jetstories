package id.furqoncreative.jetstories.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryRow(
    story: Story?,
    onStoryClicked: (Story?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier
        .height(300.dp)
        .fillMaxWidth(), onClick = {
        onStoryClicked(story)
    }) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (story?.name ?: "Null").uppercase()[0].toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                Text(
                    text = story?.name ?: "Null",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            AsyncImage(
                model = story?.photoUrl,
                contentDescription = story?.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 150.dp)
            )
        }
    }

}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun StoryRowPreview() {
    JetStoriesTheme {
        StoryRow(story = Story(
            id = "",
            name = "user10",
            description = "tes",
            photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1687244051069_oR9OxeKS.jpg",
            createdAt = "",
            lat = null,
            lon = null,
        ), onStoryClicked = {})
    }

}