package id.furqoncreative.jetstories.ui.components

import id.furqoncreative.jetstories.util.TextFieldState
import java.util.regex.Pattern

private const val EMAIL_VALIDATION_REGEX = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}\$"

class EmailState : TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError)

private fun emailValidationError(): String {
    return "Email must be valid"
}

private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
}