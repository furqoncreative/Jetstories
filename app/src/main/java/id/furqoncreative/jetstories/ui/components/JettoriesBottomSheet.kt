package id.furqoncreative.jetstories.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.furqoncreative.jetstories.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetstoriesBottomSheet(
    modifier: Modifier = Modifier,
    openBottomSheet: MutableState<Boolean>,
    onGalleryOptionClick: () -> Unit,
    onCameraOptionClick: () -> Unit
) {
    if (openBottomSheet.value) {
        ModalBottomSheet(modifier = modifier, onDismissRequest = {
            openBottomSheet.value = false
        }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(modifier = Modifier.weight(0.5f), onClick = {
                    onGalleryOptionClick()
                }) {
                    JetstoriesBottomSheetItem(
                        label = stringResource(R.string.gallery),
                        icon = Icons.Default.Image
                    )
                }

                Card(modifier = Modifier.weight(0.5f), onClick = {
                    onCameraOptionClick()
                }) {
                    JetstoriesBottomSheetItem(
                        label = stringResource(R.string.camera),
                        icon = Icons.Default.Camera
                    )
                }
            }
        }
    }
}

@Composable
fun JetstoriesBottomSheetItem(
    modifier: Modifier = Modifier, label: String, icon: ImageVector
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = label, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.width(4.dp))
        Icon(icon, contentDescription = label)
    }
}