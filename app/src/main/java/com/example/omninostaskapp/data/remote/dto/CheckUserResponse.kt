package com.example.omninostaskapp.data.remote.dto

data class CheckUserResponse(
    val message: String,
    val success: String,
    val otp: String = "",
)

