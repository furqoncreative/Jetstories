package id.furqoncreative.jetstories.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val userTokenKey = stringPreferencesKey("USER_TOKEN")

    suspend fun setUserToken(token: String) {
        dataStore.edit { settings ->
            settings[userTokenKey] = token
        }
    }

    val getUserToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[userTokenKey] ?: ""
    }

}