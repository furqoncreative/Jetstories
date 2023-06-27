package id.furqoncreative.jetstories.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val resId: Int
    ) : UiText()

    class StringResourceWithArgs(
        @StringRes val resId: Int, vararg val args: Any
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId)
            is StringResourceWithArgs -> context.getString(resId, *args)
        }
    }
}