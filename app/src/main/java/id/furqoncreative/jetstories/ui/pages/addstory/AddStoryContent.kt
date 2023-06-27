package id.furqoncreative.jetstories.ui.pages.addstory

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesDescriptionTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesProgressBar
import id.furqoncreative.jetstories.utils.DescriptionState


@Composable
fun AddStoryContent(
    modifier: Modifier = Modifier,
    context: Context,
    imageUri: Uri?,
    isLoading: Boolean,
    descriptionState: DescriptionState,
    requestStoragePermission: ManagedActivityResultLauncher<String, Boolean> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}),
    onSubmit: () -> Unit
) {
    val commonModifier = modifier.fillMaxWidth()
    Column(
        modifier = commonModifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = commonModifier
                .height(300.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                ), contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    modifier = Modifier.fillMaxSize(), painter = rememberAsyncImagePainter(
                        model = imageUri
                    ), contentDescription = stringResource(id = R.string.add_story)
                )
            } else {
                Image(
                    modifier = Modifier.size(150.dp), painter = painterResource(
                        id = R.drawable.img_add_story_placeholder
                    ), contentDescription = stringResource(id = R.string.add_story)
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            requestStoragePermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            requestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.FileUpload,
                    contentDescription = stringResource(R.string.add_image),
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = if (imageUri == null) stringResource(id = R.string.add_image) else stringResource(
                        R.string.replace_media
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        JetstoriesDescriptionTextField(context = context, onImeAction = {
            onSubmit()
        }, descriptionState = descriptionState)

        Button(modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
            enabled = descriptionState.isValid && imageUri != null,
            onClick = {
                onSubmit()
            }) {
            if (!isLoading) {
                Text(text = stringResource(R.string.submit))
            } else {
                JetstoriesProgressBar(size = 30.dp)
            }
        }
    }
}