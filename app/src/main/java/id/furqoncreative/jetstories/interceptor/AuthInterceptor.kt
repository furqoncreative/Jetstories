package id.furqoncreative.jetstories.interceptor

import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interceptor which adds authorization token in header.
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val preferencesManager: PreferencesManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val token = preferencesManager.getUserToken.first()

        val authRequest = chain.request().newBuilder().apply {
            header("Authorization", "Bearer $token")
        }.build()

        chain.proceed(authRequest)
    }
}
