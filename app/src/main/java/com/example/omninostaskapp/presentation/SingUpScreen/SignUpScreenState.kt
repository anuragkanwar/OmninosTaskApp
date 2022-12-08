package com.example.omninostaskapp.presentation.SingUpScreen


data class TextFieldState(
    val text : String = "",
    val hintText : String = "",
    val isHintVisible : Boolean = true
)

data class PasswordTextFieldState(
    val text : String = "",
    val hintText : String = "",
    val isHintVisible : Boolean = true,
    val passwordVisible : Boolean = true,
)
