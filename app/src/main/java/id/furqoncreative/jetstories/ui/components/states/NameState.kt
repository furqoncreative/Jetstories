package id.furqoncreative.jetstories.ui.components.states

import android.content.Context
import id.furqoncreative.jetstories.R

class NameState : TextFieldState(validator = ::isNameValid, errorFor = ::nameValidationError)

private fun nameValidationError(context: Context): String {
    return context.getString(R.string.name_invalid_message)
}

private fun isNameValid(name: String) = name.isNotBlank() && name.isNotEmpty()
