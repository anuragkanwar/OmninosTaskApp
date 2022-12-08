package com.example.omninostaskapp.data.remote.dto

data class ForgotPasswordResponse(
    val message: String,
    val otp: String = "",
    val success: String
)