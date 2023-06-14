package id.furqoncreative.jetstories

import android.app.Application
import id.furqoncreative.jetstories.data.AppContainer
import id.furqoncreative.jetstories.data.DefaultAppContainer

class JetstoriesApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}