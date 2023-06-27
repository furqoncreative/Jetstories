package id.furqoncreative.jetstories.utils

import android.content.Context
import id.furqoncreative.jetstories.R

class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(context: Context): String? {
        return if (showErrors()) {
            passwordConfirmationError(context)
        } else {
            null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}

private fun isPasswordValid(password: String): Boolean {
    return password.length > 7 && password.isNotBlank()
}

private fun passwordValidationError(context: Context): String {
    return context.getString(R.string.password_invalid_message)
}

private fun passwordConfirmationError(context: Context): String {
    return context.getString(R.string.confirm_password_invalid_message)
}
