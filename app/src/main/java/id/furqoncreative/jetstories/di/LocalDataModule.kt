package id.furqoncreative.jetstories.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.data.source.local.StoryDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "jetstories-preferences")

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext app: Context): DataStore<Preferences> = app.dataStore

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): StoryDatabase =
        StoryDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun providePreferencesManager(dataStore: DataStore<Preferences>): PreferencesManager =
        PreferencesManager(dataStore)
}