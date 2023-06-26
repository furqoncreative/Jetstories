package id.furqoncreative.jetstories.ui.pages.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNavUp: () -> Unit,
    collapsingToolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
) {
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
                "Settings", style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = textSize,
                    fontWeight = FontWeight.Medium
                ), modifier = Modifier
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
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                elevation = CardDefaults.elevatedCardElevation(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                onClick = {},
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Language", style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = "Change the app language",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                    Text(text = "🇬🇧", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.NEXUS_6)
@Composable
fun SettingsScreenPreview() {
    JetStoriesTheme {
        SettingsScreen(onNavUp = {})
    }
}