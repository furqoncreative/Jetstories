package id.furqoncreative.jetstories.ui.pages.mapview

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.model.stories.Story
import id.furqoncreative.jetstories.utils.showToast
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun MapViewScreen(
    modifier: Modifier = Modifier,
    onNavUp: () -> Unit,
    onClickStory: (Story) -> Unit,
    mapViewViewModel: MapViewViewModel
) {
    val uiState by mapViewViewModel.uiState.collectAsStateWithLifecycle()
    val collapsingToolbarScaffoldState: CollapsingToolbarScaffoldState =
        rememberCollapsingToolbarScaffoldState()
    val context = LocalContext.current

    uiState.userMessage?.let { userMessage ->
        context.showToast(userMessage.asString(context))
        mapViewViewModel.toastMessageShown()
    }

    CollapsingToolbarScaffold(modifier = modifier,
        state = collapsingToolbarScaffoldState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(22.dp)
                    .pin()
                    .background(color = MaterialTheme.colorScheme.background)
            )
            Text(
                "Map View Stories", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold, fontSize = 18.sp
                ), modifier = Modifier
                    .padding(
                        top = 10.dp, start = 40.dp, bottom = 16.dp
                    )
                    .road(whenCollapsed = Alignment.TopStart, whenExpanded = Alignment.TopStart)
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

        MapViewContent(
            stories = uiState.stories,
            selectedStory = uiState.selectedStory,
            isMyLocationEnabled = mapViewViewModel.hasLocationPermission(context = context),
            onClickStory = { story ->
                onClickStory(story)
            },
            setSelectedStory = { story ->
                mapViewViewModel.setSelectedMapStory(story)
            },
            clearSelectedStory = {
                mapViewViewModel.clearSelectedMapStory()
            })

    }
}