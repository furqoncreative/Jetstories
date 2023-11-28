package id.furqoncreative.jetstories.ui.pages.mapview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import id.furqoncreative.jetstories.model.stories.Story
import kotlinx.coroutines.launch
import id.furqoncreative.jetstories.R

@Composable
fun MapViewContent(
    modifier: Modifier = Modifier,
    isMyLocationEnabled: Boolean,
    stories: List<Story>?,
    selectedStory: Story?,
    onClickStory: (Story) -> Unit,
    setSelectedStory: (Story) -> Unit,
    clearSelectedStory: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isMapLoaded by remember { mutableStateOf(false) }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = false,
                mapToolbarEnabled = false,
                rotationGesturesEnabled = true,
                zoomControlsEnabled = false,
            )
        )
    }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_styles),
                isMyLocationEnabled = isMyLocationEnabled,
                minZoomPreference = 2f,
                maxZoomPreference = 15f
            )
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-6.905977, 107.613144), 7.5F)
    }

    Box(modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            googleMapOptionsFactory = {
                GoogleMapOptions()
            },
            properties = properties,
            uiSettings = uiSettings,
            onMapLoaded = {
                isMapLoaded = true
            }
        ) {

            if (!stories.isNullOrEmpty()) {
                for (story in stories) {
                    val location = LatLng(story.lat ?: 0.0, story.lon ?: 0.0)

                    val markerState by remember {
                        mutableStateOf(MarkerState(location))
                    }

                    MarkerInfoWindow(state = markerState,
                        anchor = if (selectedStory == story) {
                            Offset(.5f, .7f)
                        } else {
                            Offset(.5f, 1f)
                        },
                        title = story.name,
                        snippet = "Click for details",
                        icon = if (selectedStory == story) {
                            BitmapDescriptorFactory.defaultMarker(100F)
                        } else {
                            BitmapDescriptorFactory.defaultMarker()
                        },
                        onClick = {
                            setSelectedStory(story)
                            false
                        },
                        onInfoWindowClick = {
                            scope.launch {
                                onClickStory(story)
                            }
                        },
                        onInfoWindowClose = {
                            clearSelectedStory()
                        }) { marker ->

                        Column(
                            Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(8.dp)
                                )
                                .background(MaterialTheme.colorScheme.background)
                                .padding(8.dp, 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = marker.title ?: "Name",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = marker.snippet ?: "Story", fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
        if (!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier.matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentSize()
                )
            }
        }
    }
}