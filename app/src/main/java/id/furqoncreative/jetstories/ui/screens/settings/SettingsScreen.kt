package id.furqoncreative.jetstories.ui.screens.settings

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesAlertDialog
import id.furqoncreative.jetstories.ui.components.JetstoriesHeader
import id.furqoncreative.jetstories.ui.components.JetstoriesSnackBarHost
import id.furqoncreative.jetstories.ui.components.TitleToolbar
import id.furqoncreative.jetstories.ui.components.showSnackBar
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onNavUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()
    val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let {
            showSnackBar(
                snackbarHostState = snackbarHostState,
                actionLabel = "OK",
                message = it.asString(context),
            )
        }
        settingsViewModel.toastMessageShown()
    }

    Box(
        modifier = modifier.fillMaxHeight(),
    ) {
        JetstoriesHeader(
            modifier = modifier.align(Alignment.TopCenter),
            state = collapsingToolbarScaffoldState,
            titleToolbarContent = {
                TitleToolbar(
                    modifier = Modifier
                        .padding(
                            top = 10.dp, start = 40.dp, bottom = 16.dp, end = 40.dp
                        ),
                    title = stringResource(id = R.string.settings),
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
            SettingsContent(
                selectedLanguageEnum = uiState.selectedLanguageEnum,
                onSetAppLanguage = { settingsViewModel.setAppLanguage() },
                onSetSelectedLanguage = { selectedLanguage ->
                    settingsViewModel.setSelectedLanguage(selectedLanguage)
                })
        }

        JetstoriesSnackBarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true, device = Devices.NEXUS_6)
@Composable
fun SettingsScreenPreview() {
    JetStoriesTheme {
        SettingsScreen(onNavUp = {}, settingsViewModel = hiltViewModel())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    selectedLanguageEnum: LanguageEnum,
    onSetAppLanguage: () -> Unit,
    onSetSelectedLanguage: (LanguageEnum) -> Unit,
    modifier: Modifier = Modifier,
    alertDialogState: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    val languageOption = LanguageEnum.entries.toTypedArray()
    val (selectedLanguage, onSelectedLanguage) = remember { mutableStateOf(selectedLanguageEnum) }

    val commonModifier = modifier.fillMaxWidth()

    JetstoriesAlertDialog(modifier = modifier,
        openDialog = alertDialogState,
        title = stringResource(R.string.change_language),
        confirmText = stringResource(R.string.confirm),
        dismissText = stringResource(R.string.cancel),
        confirmAction = {
            onSetAppLanguage()
        },
        icon = {
            Icon(Icons.Filled.Language, contentDescription = null)
        },
        content = {
            Column(Modifier.selectableGroup()) {
                languageOption.forEach { option ->
                    Row(
                        commonModifier
                            .height(56.dp)
                            .selectable(
                                selected = (option == selectedLanguage), onClick = {
                                    onSelectedLanguage(option)
                                    onSetSelectedLanguage(option)
                                }, role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = commonModifier,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = (option == selectedLanguage), onClick = {
                                onSelectedLanguage(option)
                                onSetSelectedLanguage(option)
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
        modifier = commonModifier
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(elevation = CardDefaults.elevatedCardElevation(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
            onClick = {
                alertDialogState.value = true
            }) {
            Row(
                modifier = commonModifier.padding(vertical = 8.dp, horizontal = 16.dp),
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
                    text = selectedLanguageEnum.icon,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}