package id.furqoncreative.jetstories.ui.screens.detailstory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.ui.components.JetstoriesHeader
import id.furqoncreative.jetstories.ui.components.JetstoriesIconButton
import id.furqoncreative.jetstories.ui.components.JetstoriesLinearProgressBar
import id.furqoncreative.jetstories.ui.components.TitleToolbar
import id.furqoncreative.jetstories.utils.showToast
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun DetailStoryScreen(
    detailStoryViewModel: DetailStoryViewModel,
    onNavUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiState by detailStoryViewModel.uiState.collectAsStateWithLifecycle()
    val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val story = uiState.story

    uiState.userMessage?.let { userMessage ->
        context.showToast(userMessage.asString(context))
        detailStoryViewModel.toastMessageShown()
    }

    if (story != null) {
        JetstoriesHeader(
            modifier = modifier,
            state = collapsingToolbarScaffoldState,
            titleToolbarContent = { textSize ->
                TitleToolbar(
                    modifier = Modifier
                        .padding(
                            top = 10.dp, start = 40.dp, bottom = 16.dp, end = 40.dp
                        ),
                    title = stringResource(
                        id = R.string.detail_story,
                        story.name.replaceFirstChar { it.uppercase() }),
                    textSize = textSize
                )
            },
            startToolbarContent = {
                JetstoriesIconButton(
                    icon = Icons.Default.ChevronLeft,
                    contentDescription = stringResource(id = R.string.back)
                ) {
                    onNavUp()
                }
            }, endToolbarContent = {
                IconButton(
                    onClick = {
                        detailStoryViewModel.setFavorite(
                            isFavorite = uiState.isFavorite,
                            story = story
                        )
                    }) {
                    if (uiState.isFavorite) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite")
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "UnFavorite"
                        )
                    }
                }
            }
        ) {
            DetailStoryContent(
                isLoading = uiState.isLoading,
                story = uiState.story
            )
        }
    }
}

@Composable
fun DetailStoryContent(
    story: Story?,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val commonModifier = modifier.fillMaxWidth()

    if (isLoading) {
        JetstoriesLinearProgressBar()
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