package id.furqoncreative.jetstories.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

enum class MenuItem(val title: String) {
    LOGOUT("Logout"), SETTINGS("Settings")
}

@Composable
fun OptionMenu(
    modifier: Modifier = Modifier,
    onClickMenu: Map<MenuItem, () -> Unit>,
    expanded: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    DropdownMenu(modifier = modifier,
        expanded = expanded.value,
        offset = DpOffset((-40).dp, (-40).dp),
        onDismissRequest = { expanded.value = false }) {
        enumValues<MenuItem>().forEach { menuItem ->
            DropdownMenuItem(onClick = {
                when (menuItem) {
                    MenuItem.LOGOUT -> {
                        onClickMenu[MenuItem.LOGOUT]?.invoke()
                    }

                    MenuItem.SETTINGS -> {
                        onClickMenu[MenuItem.SETTINGS]?.invoke()
                    }
                }
                expanded.value = false
            }, text = {
                Text(text = menuItem.title)
            })
        }
    }
}