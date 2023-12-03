package id.furqoncreative.jetstories.ui.components.states

import android.content.Context
import id.furqoncreative.jetstories.R

class DescriptionState : TextFieldState(validator = ::isNameValid, errorFor = ::nameValidationError)

private fun nameValidationError(context: Context): String {
    return context.getString(R.string.description_invalid_message)
}

private fun isNameValid(name: String) = name.isNotBlank() && name.isNotEmpty()
