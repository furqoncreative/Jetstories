package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.R

@Composable
fun JetstoriesCircularProgressBar(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = modifier.size(size), color = MaterialTheme.colorScheme.onSecondary
        )
        Text(text = stringResource(R.string.loading))
    }
}