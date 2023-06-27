package id.furqoncreative.jetstories.utils

import android.content.Context
import id.furqoncreative.jetstories.R

class NameState : TextFieldState(validator = ::isNameValid, errorFor = ::nameValidationError)

private fun nameValidationError(context: Context): String {
    return context.getString(R.string.name_invalid_message)
}

private fun isNameValid(name: String) = name.isNotBlank() && name.isNotEmpty()
