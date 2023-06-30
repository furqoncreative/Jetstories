package id.furqoncreative.jetstories.utils

import android.content.Context
import android.os.Environment
import android.widget.Toast
import id.furqoncreative.jetstories.R
import id.furqoncreative.jetstories.ui.components.MenuItem
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

fun Context.createCustomTempFile(): File {
    val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT, Locale.US
    ).format(System.currentTimeMillis())

    val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
    val storageDir = cacheDir
    return File.createTempFile(
        "JPEG_${timeStamp}_", ".jpg", storageDir
    ).apply {
        deleteOnExit()
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.getMenuItemStringResource(menuItem: MenuItem) = when (menuItem) {
    MenuItem.LOGOUT -> this.getString(R.string.logout)
    MenuItem.SETTINGS -> this.getString(R.string.settings)
}