package id.furqoncreative.jetstories.ui.pages.addstory

import android.Manifest
import android.net.Uri
import android.os.Build
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
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesDescriptionTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesProgressBar
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.utils.SquireCropImage
import id.furqoncreative.jetstories.utils.showToast
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.io.File
import java.util.Date

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddStoryScreen(
    modifier: Modifier = Modifier,
    onNavUp: () -> Unit,
    onSuccessAddStory: () -> Unit,
    collapsingToolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    addStoryViewModel: AddStoryViewModel = hiltViewModel()
) {
    val uiState by addStoryViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val uCropLauncher = rememberLauncherForActivityResult(SquireCropImage()) { uri ->
        addStoryViewModel.setImageUri(uri)
    }

    val imageSelectorLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                uCropLauncher.launch(
                    Pair(
                        first = uri, second = Uri.fromFile(
                            File(context.cacheDir, "temp_image_file_${Date().time}")
                        )
                    )
                )
            } else {
                addStoryViewModel.setUserMessage(context.getString(R.string.no_image_selected))
            }
        }

    val requestStoragePermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                imageSelectorLauncher.launch("image/*")
            } else {
                addStoryViewModel.setUserMessage(context.getString(R.string.allow_storage_permission))
            }
        }

    val onSubmit = {
        if (uiState.descriptionState.isValid) {
            addStoryViewModel.addStory(
                context = context
            )
            keyboardController?.hide()
        }
    }

    LaunchedEffect(uiState.isSuccessAddStory) {
        if (uiState.isSuccessAddStory) {
            onSuccessAddStory()
        }
    }

    uiState.userMessage?.let { userMessage ->
        context.showToast(message = userMessage.asString(context))
        addStoryViewModel.toastMessageShown()
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
                text = stringResource(R.string.add_story),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = textSize,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
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
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = stringResource(
                            R.string.back
                        )
                    )
                }
            }
        }) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    ), contentAlignment = Alignment.Center
            ) {
                if (uiState.imageUri != null) {
                    Image(
                        modifier = Modifier.fillMaxSize(), painter = rememberAsyncImagePainter(
                            model = uiState.imageUri
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
                        text = if (uiState.imageUri == null) stringResource(id = R.string.add_image) else stringResource(
                            R.string.replace_media
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            JetstoriesDescriptionTextField(context = context, onImeAction = {
                onSubmit()
            }, descriptionState = uiState.descriptionState)

            Button(modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
                enabled = uiState.descriptionState.isValid && uiState.imageUri != null,
                onClick = {
                    onSubmit()
                }) {
                if (!uiState.isLoading) {
                    Text(text = stringResource(R.string.submit))
                } else {
                    JetstoriesProgressBar(size = 30.dp)
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun AddStoryScreenPreview() {
    JetStoriesTheme {
        AddStoryScreen(onNavUp = { /*TODO*/ }, onSuccessAddStory = {})
    }
}