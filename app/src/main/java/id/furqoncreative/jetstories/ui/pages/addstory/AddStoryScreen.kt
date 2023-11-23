package id.furqoncreative.jetstories.ui.pages.addstory

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.theme.JetStoriesTheme
import id.furqoncreative.jetstories.utils.SquireCropImage
import id.furqoncreative.jetstories.utils.createImageFile
import id.furqoncreative.jetstories.utils.getUriForFile
import id.furqoncreative.jetstories.utils.showToast
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
    addStoryViewModel: AddStoryViewModel = hiltViewModel()
) {
    val uiState by addStoryViewModel.uiState.collectAsStateWithLifecycle()
    val collapsingToolbarScaffoldState: CollapsingToolbarScaffoldState =
        rememberCollapsingToolbarScaffoldState()
    val bottomSheetState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val shareLocationState: MutableState<Boolean> = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val uCropLauncher = rememberLauncherForActivityResult(SquireCropImage()) { uri ->
        addStoryViewModel.setImageUri(uri)
    }

    val scope = rememberCoroutineScope()

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

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
        AddStoryScreen(onNavUp = { /*TODO*/ }, onSuccessAddStory = {})
    }
}