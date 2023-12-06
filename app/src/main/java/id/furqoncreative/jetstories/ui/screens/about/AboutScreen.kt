package id.furqoncreative.jetstories.ui.screens.about

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesHeader
import id.furqoncreative.jetstories.ui.components.TitleToolbar
import id.furqoncreative.jetstories.ui.screens.settings.SettingsContent
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun AboutScreen(
    onNavUp : () -> Unit,
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
fun AboutContent() {

}
