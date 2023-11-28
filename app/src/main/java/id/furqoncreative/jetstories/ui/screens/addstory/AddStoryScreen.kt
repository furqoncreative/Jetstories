package id.furqoncreative.jetstories.ui.screens.addstory

import android.Manifest
import android.content.Context
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
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.LocationServices
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.JetstoriesBottomSheet
import id.furqoncreative.jetstories.ui.components.JetstoriesDescriptionTextField
import id.furqoncreative.jetstories.ui.components.JetstoriesHeader
import id.furqoncreative.jetstories.ui.components.JetstoriesProgressBar
import id.furqoncreative.jetstories.ui.components.TitleToolbar
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.utils.DescriptionState
import id.furqoncreative.jetstories.utils.SquireCropImage
import id.furqoncreative.jetstories.utils.createImageFile
import id.furqoncreative.jetstories.utils.getUriForFile
import id.furqoncreative.jetstories.utils.showToast
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.io.File
import java.util.Date

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddStoryScreen(
    addStoryViewModel: AddStoryViewModel,
    onNavUp: () -> Unit,
    onSuccessAddStory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val uiState by addStoryViewModel.uiState.collectAsStateWithLifecycle()
    val collapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    val bottomSheetState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val shareLocationState: MutableState<Boolean> = remember { mutableStateOf(false) }

    val uCropLauncher = rememberLauncherForActivityResult(SquireCropImage()) { uri ->
        addStoryViewModel.setImageUri(uri)
    }

    val scope = rememberCoroutineScope()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

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

            bottomSheetState.value = false
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                uiState.imageUri?.let { uri ->
                    uCropLauncher.launch(
                        Pair(
                            first = uri, second = Uri.fromFile(
                                File(context.cacheDir, "temp_image_file_${Date().time}")
                            )
                        )
                    )
                }
            } else {
                addStoryViewModel.setUserMessage(context.getString(R.string.cannot_save_the_image))
            }

            bottomSheetState.value = false
        }

    val requestStoragePermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                imageSelectorLauncher.launch("image/*")
            } else {
                addStoryViewModel.setUserMessage(context.getString(R.string.allow_storage_permission))
            }
        }

    val requestLocationPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionGranted ->
            val isGranted =
                permissionGranted.containsKey(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .or(permissionGranted.containsKey(Manifest.permission.ACCESS_FINE_LOCATION))
            if (isGranted) {
                scope.launch {
                    val location = fusedLocationClient.lastLocation.await()
                    addStoryViewModel.setLocation(location)
                }
            } else {
                addStoryViewModel.setUserMessage(context.getString(R.string.allow_location_permission))
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

    JetstoriesHeader(
        state = collapsingToolbarScaffoldState,
        titleToolbarContent = {
            TitleToolbar(
                modifier = Modifier
                    .padding(
                        top = 10.dp, start = 40.dp, bottom = 16.dp, end = 40.dp
                    ),
                title = stringResource(id = R.string.add_story),
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
                        R.string.back
                    )
                )
            }
        }
    ) {
        AddStoryContent(
            context = context,
            isLoading = uiState.isLoading,
            imageUri = uiState.imageUri,
            descriptionState = uiState.descriptionState,
            shareLocationState = shareLocationState,
            bottomSheetState = bottomSheetState,
            onCameraOptionClick = {
                val newPhotoUri = context.createImageFile().getUriForFile(context)

                addStoryViewModel.setImageUri(newPhotoUri)

                cameraLauncher.launch(newPhotoUri)
            },
            onGalleryOptionClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestStoragePermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    requestStoragePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            },
            onLocationEnable = { isChecked ->
                if (isChecked) {
                    requestLocationPermission.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            onSubmit = {
                onSubmit()
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AddStoryScreenPreview() {
    JetStoriesTheme {
        AddStoryScreen(onNavUp = { }, onSuccessAddStory = {}, addStoryViewModel = hiltViewModel())
    }
}

@Composable
fun AddStoryContent(
    context: Context,
    isLoading: Boolean,
    imageUri: Uri?,
    descriptionState: DescriptionState,
    bottomSheetState: MutableState<Boolean>,
    shareLocationState: MutableState<Boolean>,
    onCameraOptionClick: () -> Unit,
    onGalleryOptionClick: () -> Unit,
    onLocationEnable: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
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

        Row(
            modifier = Modifier.align(Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = stringResource(R.string.share_location),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = shareLocationState.value,
                onCheckedChange = { isChecked ->
                    shareLocationState.value = isChecked
                    onLocationEnable(isChecked)
                }
            )
        }

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