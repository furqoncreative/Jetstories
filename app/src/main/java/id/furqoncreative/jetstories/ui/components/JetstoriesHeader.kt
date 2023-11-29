package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy

@Composable
fun JetstoriesHeader(
    state: CollapsingToolbarScaffoldState,
    titleToolbarContent: @Composable (TextUnit) -> Unit,
    modifier: Modifier = Modifier,
    scrollStrategy: ScrollStrategy? = ScrollStrategy.ExitUntilCollapsed,
    startToolbarContent: @Composable () -> Unit? = {},
    endToolbarContent: @Composable () -> Unit? = {},
    isMapView: Boolean? = false,
    bodyContent: @Composable () -> Unit
) {
    CollapsingToolbarScaffold(modifier = modifier,
        state = state,
        scrollStrategy = scrollStrategy ?: ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val textSize =  if(!isMapView!!) {
                (20 + (30 - 12) * state.toolbarState.progress).sp
            } else {
                20.sp
            }

            if (!isMapView) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .pin()
                        .background(color = MaterialTheme.colorScheme.background)
                )
            }

            Row(
                modifier = Modifier.road(
                    whenExpanded = Alignment.BottomStart, whenCollapsed = Alignment.TopStart
                )
            ) {
                startToolbarContent()
            }

            Row(
                modifier = Modifier.road(
                    whenCollapsed = Alignment.TopStart,
                    whenExpanded = Alignment.Center
                )
            ) {
                titleToolbarContent(textSize)
            }

            Row(
                modifier = Modifier.road(
                    whenExpanded = Alignment.BottomEnd, whenCollapsed = Alignment.TopEnd
                )
            ) {
                endToolbarContent()
            }


        }) { bodyContent() }
}

@Composable
fun TitleToolbar(
    modifier: Modifier = Modifier,
    title: String,
    textSize: TextUnit
) {
    Text(
        modifier = modifier,
        text = title,
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = textSize,
            fontWeight = FontWeight.Medium
        ),
    )
}