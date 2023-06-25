package id.furqoncreative.jetstories.utils

class DescriptionState : TextFieldState(validator = ::isNameValid, errorFor = ::nameValidationError)

private fun nameValidationError(): String {
    return "Description must not empty"
}

private fun isNameValid(name: String) = name.isNotBlank() && name.isNotEmpty()
