package com.example.omninostaskapp.data.remote.dto

import com.example.omninostaskapp.data.remote.dto.Details


data class RegisterResponse(
    val details: Details?,
    val message: String,
    val success: String
)

