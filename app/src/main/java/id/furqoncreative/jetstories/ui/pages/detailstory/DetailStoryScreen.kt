package id.furqoncreative.jetstories.ui.pages.detailstory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@Composable
fun DetailStoryScreen(
    storyViewModel: DetailStoryViewModel = hiltViewModel()
) {
    val uiState by storyViewModel.uiState.collectAsStateWithLifecycle()
    val story = uiState.story

    Column {
        if (story != null) {
            AsyncImage(
                model = story.photoUrl,
                contentDescription = story.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 150.dp)
            )
        }
    }
}