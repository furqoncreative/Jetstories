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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesAlertDialog
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.utils.showToast
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
    alertDialogState: MutableState<Boolean> = remember { mutableStateOf(false) },
    settingsViewModel: SettingsViewModel = hiltViewModel()

) {
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val languageOption = LanguageEnum.values()
    val (selectedLanguage, onSelectedLanguage) = remember { mutableStateOf(uiState.selectedLanguageEnum) }

    uiState.userMessage?.let { userMessage ->
        context.showToast(message = userMessage.asString(context))
    }

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

        JetstoriesAlertDialog(openDialog = alertDialogState,
            title = stringResource(R.string.change_language),
            confirmText = stringResource(R.string.confirm),
            dismissText = stringResource(R.string.cancel),
            confirmAction = {
                settingsViewModel.setAppLanguage()
            },
            icon = {
                Icon(Icons.Filled.Language, contentDescription = null)
            },
            content = {
                Column(Modifier.selectableGroup()) {
                    languageOption.forEach { option ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (option == selectedLanguage), onClick = {
                                        onSelectedLanguage(option)
                                        settingsViewModel.setSelectedLanguage(option)
                                    }, role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(selected = (option == selectedLanguage), onClick = {
                                    onSelectedLanguage(option)
                                    settingsViewModel.setSelectedLanguage(option)
                                })
                                Text(
                                    text = option.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                Text(
                                    text = option.icon,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }

                        }
                    }
                }
            })

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                elevation = CardDefaults.elevatedCardElevation(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                onClick = {
                    alertDialogState.value = true
//                    context.startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                },
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(id = R.string.language),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(id = R.string.choose_language),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                    Text(
                        text = uiState.selectedLanguageEnum.icon,
                        style = MaterialTheme.typography.titleMedium
                    )
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