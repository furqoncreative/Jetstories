package id.furqoncreative.jetstories.ui.components

import id.furqoncreative.jetstories.util.TextFieldState

class NameState : TextFieldState(validator = ::isNameValid, errorFor = ::nameValidationError)

private fun nameValidationError(): String {
    return "Name must not empty"
}

private fun isNameValid(name: String) = name.isNotBlank() && name.isNotEmpty()
