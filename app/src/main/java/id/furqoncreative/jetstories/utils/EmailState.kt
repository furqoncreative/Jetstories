package id.furqoncreative.jetstories.utils

import android.content.Context
import id.furqoncreative.jetstories.R
import java.util.regex.Pattern

private const val EMAIL_VALIDATION_REGEX = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}\$"

class EmailState : TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError)

private fun emailValidationError(context: Context): String {
    return context.getString(R.string.email_invalid_message)
}

private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
}