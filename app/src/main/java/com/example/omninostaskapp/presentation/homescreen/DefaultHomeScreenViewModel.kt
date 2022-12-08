package com.example.omninostaskapp.presentation.homescreen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omninostaskapp.domain.repositories.Repository
import com.example.omninostaskapp.presentation.BaseViewModel
import com.example.omninostaskapp.presentation.ResponseEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DefaultHomeScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private val _userEmail = mutableStateOf(
        TextFieldState(
            hintText = "Enter Email..."
        )
    )
    val userEmail: State<TextFieldState> = _userEmail

    private val _userPassword = mutableStateOf(
        PasswordTextFieldState(
            hintText = "Enter Password..."
        )
    )
    val userPassword: State<PasswordTextFieldState> = _userPassword

    private val _userForgotPasswordPhone = mutableStateOf("")
    val userForgotPasswordPhone: State<String> = _userForgotPasswordPhone

//    var isLoading = mutableStateOf(false)

    private val _isSuccessForgotPassword = mutableStateOf(false)
    val isSuccessForgotPassword: State<Boolean> = _isSuccessForgotPassword

    private val _loadErrorForgotPassword = mutableStateOf("")
    val loadErrorForgotPassword: State<String> = _loadErrorForgotPassword

    private val _isSuccessChangePassword = mutableStateOf(false)
    val isSuccessChangePassword: State<Boolean> = _isSuccessChangePassword

    private val _loadErrorChangePassword = mutableStateOf("")
    val loadErrorChangePassword: State<String> = _loadErrorChangePassword

    fun changeForgotPasswordState(bool: Boolean) {
        _isSuccessForgotPassword.value = bool
    }


    fun changePassword(
        phn: String, pass: String
    ) {
        viewModelScope.launch {
            _loadErrorChangePassword.value = ""
            _isSuccessChangePassword.value = false
            val result = repository.changePassword(
                phone = phn,
                newPassword = pass
            )
            if (result.data == null) {
                _loadErrorChangePassword.value = result.e.toString()
            } else {
                if (result.data!!.body()!!.success == "1") {
                    _isSuccessChangePassword.value = true
                    _loadErrorChangePassword.value = ""
                } else {
                    _loadErrorChangePassword.value = result.data!!.body()!!.message
                }
            }

        }
    }

    fun forgotPassword(
        phn: String
    ) {
        viewModelScope.launch {

            _loadErrorForgotPassword.value = ""
            _isSuccessForgotPassword.value = false

            val result = repository.forgotPassword(
                phone = phn
            )

            if (result.data == null) {
                _loadErrorForgotPassword.value = result.e.toString()
            } else {
                if (result.data!!.body()!!.success == "1") {
                    _isSuccessForgotPassword.value = true
                    _loadErrorForgotPassword.value = ""
                } else {
                    _loadErrorForgotPassword.value = result.data!!.body()!!.message
                }
            }
        }
    }


    fun registerUser(
        email: String,
        user: String,
        pwd: String,
        phn: String,
        reg: String,
        dt: String,
        lt: String,
        lat: String,
        lon: String,
        ch: String
    ) {
        viewModelScope.launch {
            val response = repository.registerUser(
                email,
                user,
                pwd,
                phn,
                reg,
                dt,
                lt,
                lat,
                lon,
                ch
            )
//            myPushResponse.value = response
            Log.d("PKDO", response.data?.body().toString())
        }
    }


    fun onEvent(event: UserEvents) {
        when (event) {
            is UserEvents.EnteredEmail -> {
                _userEmail.value = _userEmail.value.copy(
                    text = event.value
                )
            }
            is UserEvents.EnteredPassword -> {
                _userPassword.value = _userPassword.value.copy(
                    text = event.value
                )
            }
            is UserEvents.ChangeEmailFocus -> {
                _userEmail.value = _userEmail.value.copy(
                    isHintVisible = !event.focusState.isFocused && userEmail.value.text.isBlank()
                )
            }
            is UserEvents.ChangePasswordFocus -> {
                _userPassword.value = _userPassword.value.copy(
                    isHintVisible = !event.focusState.isFocused && userPassword.value.text.isBlank()
                )
            }
            is UserEvents.ChangePasswordVisibility -> {
                _userPassword.value = _userPassword.value.copy(
                    passwordVisible = !userPassword.value.passwordVisible
                )
            }

            is UserEvents.Login -> {

            }
        }
    }
}


sealed class UserEvents {
    data class EnteredEmail(val value: String) : UserEvents()
    data class EnteredPassword(val value: String) : UserEvents()
    data class ChangeEmailFocus(val focusState: FocusState) : UserEvents()
    data class ChangePasswordFocus(val focusState: FocusState) : UserEvents()
    object ChangePasswordVisibility : UserEvents()
    object Login : UserEvents()
}

