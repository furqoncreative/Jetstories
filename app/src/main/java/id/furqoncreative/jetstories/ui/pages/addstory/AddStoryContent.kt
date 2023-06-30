package id.furqoncreative.jetstories.ui.pages.addstory

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesBottomSheet
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
    onCameraOptionClick: () -> Unit,
    onGalleryOptionClick: () -> Unit,
    bottomSheetState: MutableState<Boolean>,
    onSubmit: () -> Unit
) {
    val commonModifier = modifier.fillMaxWidth()

    JetstoriesBottomSheet(openBottomSheet = bottomSheetState, onCameraOptionClick = {
        onCameraOptionClick()
    }, onGalleryOptionClick = {
        onGalleryOptionClick()
    })

    Column(
        modifier = commonModifier
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Top
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
                        bottomSheetState.value = true
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

        Spacer(modifier = Modifier.height(16.dp))

        JetstoriesDescriptionTextField(context = context, onImeAction = {
            onSubmit()
        }, descriptionState = descriptionState)

        Button(modifier = commonModifier.height(56.dp),
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
