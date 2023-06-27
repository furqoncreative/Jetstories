package id.furqoncreative.jetstories.ui.pages.detailstory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.utils.showToast
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun DetailStoryScreen(
    modifier: Modifier = Modifier,
    onNavUp: () -> Unit,
    storyViewModel: DetailStoryViewModel = hiltViewModel()
) {
    val uiState by storyViewModel.uiState.collectAsStateWithLifecycle()
    val collapsingToolbarScaffoldState: CollapsingToolbarScaffoldState =
        rememberCollapsingToolbarScaffoldState()
    val context = LocalContext.current
    val story = uiState.story

    uiState.userMessage?.let { userMessage ->
        context.showToast(userMessage.asString(context))
        storyViewModel.toastMessageShown()
    }

    if (story != null) {
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
                    text = stringResource(id = R.string.detail_story,
                        story.name.replaceFirstChar { it.uppercase() }),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = textSize,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier
                        .padding(
                            top = 10.dp, start = 40.dp, bottom = 16.dp, end = 40.dp
                        )
                        .road(whenCollapsed = Alignment.TopStart, whenExpanded = Alignment.Center)
                )
                Row(
                    modifier = Modifier.road(
                        whenExpanded = Alignment.BottomStart, whenCollapsed = Alignment.TopStart
                    )
                ) {
                    IconButton(onClick = {
                        onNavUp()
                    }) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "Back")
                    }
                }
            }) {

            DetailStoryContent(isLoading = uiState.isLoading, story = uiState.story)
        }
    }
}