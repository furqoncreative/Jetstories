package id.furqoncreative.jetstories.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import id.furqoncreative.jetstories.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetstoriesSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = {
            onQueryChange(it)
        },
        onSearch = {
            onSearch(it)
        },
        active = false,
        onActiveChange = {},
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        placeholder = {
            Text(text = stringResource(R.string.search_placeholder))
        },
        content = {}
    )

}