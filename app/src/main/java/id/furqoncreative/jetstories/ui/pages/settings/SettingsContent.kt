package id.furqoncreative.jetstories.ui.pages.settings

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    selectedLanguageEnum: LanguageEnum,
    alertDialogState: MutableState<Boolean> = remember { mutableStateOf(false) },
    onSetAppLanguage: () -> Unit,
    onSetSelectedLanguage: (LanguageEnum) -> Unit,
) {
    val languageOption = LanguageEnum.values()
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
                    text = selectedLanguageEnum.icon, style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}