package id.furqoncreative.jetstories.util

import kotlinx.coroutines.flow.SharingStarted

private const val stopTimeoutMillis: Long = 5000
val WhileUiSubscribed: SharingStarted = SharingStarted.WhileSubscribed(stopTimeoutMillis)
