package id.furqoncreative.jetstories.ui.components

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.utils.getMenuItemContentDescription
import id.furqoncreative.jetstories.utils.getMenuItemStringResource

enum class MenuItem {
    LOGOUT, SETTINGS, ABOUT
}

@Composable
fun JetstoriesOptionMenu(
    context: Context,
    onClickMenu: Map<MenuItem, () -> Unit>,
    expanded: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded.value,
        offset = DpOffset(x = (-10).dp, y = (-40).dp),
        onDismissRequest = { expanded.value = false }) {
        enumValues<MenuItem>().forEach { menuItem ->
            DropdownMenuItem(
                onClick = {
                    when (menuItem) {
                        MenuItem.LOGOUT -> {
                            onClickMenu[MenuItem.LOGOUT]?.invoke()
                        }

                        MenuItem.SETTINGS -> {
                            onClickMenu[MenuItem.SETTINGS]?.invoke()
                        }

                        MenuItem.ABOUT -> {
                            onClickMenu[MenuItem.ABOUT]?.invoke()
                        }
                    }
                    expanded.value = false
                }, text = {
                    Text(text = context.getMenuItemStringResource(menuItem))
                }, leadingIcon = {
                    Icon(
                        imageVector = getMenuItemIcon(menuItem),
                        contentDescription = context.getMenuItemContentDescription(menuItem)
                    )
                }
            )
        }
    }
}

fun getMenuItemIcon(menuItem: MenuItem) = when (menuItem) {
    MenuItem.LOGOUT -> {
        Icons.Default.ExitToApp
    }

    MenuItem.SETTINGS -> {
        Icons.Default.Settings
    }

    MenuItem.ABOUT -> {
        Icons.Default.Info
    }
}