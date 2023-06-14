package id.furqoncreative.jetstories

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import id.furqoncreative.jetstories.data.AppContainer
import id.furqoncreative.jetstories.data.DefaultAppContainer

@HiltAndroidApp
class JetstoriesApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}