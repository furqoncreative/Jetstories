package id.furqoncreative.jetstories.ui.screens.detailstory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import id.furqoncreative.jetstories.model.stories.Story

@Composable
fun DetailStoryContent(
    modifier: Modifier = Modifier, isLoading: Boolean, story: Story?
) {
    val commonModifier = modifier.fillMaxWidth()

    if (isLoading) {
        LinearProgressIndicator(
            modifier = commonModifier.padding(16.dp), color = MaterialTheme.colorScheme.tertiary
        )
    } else {
        Column(
            modifier = commonModifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = story?.photoUrl,
                contentDescription = story?.description,
                modifier = commonModifier.defaultMinSize(minWidth = 300.dp, minHeight = 300.dp)
            )
            story?.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}