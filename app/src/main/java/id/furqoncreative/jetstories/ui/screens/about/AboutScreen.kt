package id.furqoncreative.jetstories.ui.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesHeader
import id.furqoncreative.jetstories.ui.components.TitleToolbar
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun AboutScreen(
    onNavUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    JetstoriesHeader(
        modifier = modifier,
        state = collapsingToolbarScaffoldState,
        titleToolbarContent = {
            TitleToolbar(
                modifier = Modifier
                    .padding(
                        top = 10.dp, start = 40.dp, bottom = 16.dp, end = 40.dp
                    ),
                title = stringResource(R.string.about),
                textSize = it
            )
        },
        startToolbarContent = {
            IconButton(onClick = {
                onNavUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = stringResource(
                        id = R.string.back
                    )
                )
            }
        }
    ) {
        AboutContent()
    }
}

@Composable
fun AboutContent(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = stringResource(R.string.about_avatar),
            contentDescription = stringResource(R.string.profile_picture),
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(R.string.about_name),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.about_email),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
