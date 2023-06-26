package id.furqoncreative.jetstories.ui.pages.detailstory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import id.furqoncreative.jetstories.utils.showToast
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun DetailStoryScreen(
    modifier: Modifier = Modifier,
    onNavUp: () -> Unit,
    collapsingToolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    storyViewModel: DetailStoryViewModel = hiltViewModel()
) {
    val uiState by storyViewModel.uiState.collectAsStateWithLifecycle()
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
                    text = StringBuilder("${story.name.replaceFirstChar { it.uppercase() }}'s Story").toString(),
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

            if (uiState.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.tertiary
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = story.photoUrl,
                        contentDescription = story.description,
                        modifier = Modifier
                            .fillMaxSize()
                            .defaultMinSize(minWidth = 300.dp, minHeight = 300.dp)
                    )
                    Text(
                        text = story.description,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}