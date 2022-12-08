package com.example.omninostaskapp.presentation.SingUpScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omninostaskapp.domain.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _isSuccessCheckUser = mutableStateOf(false)
    val isSuccessCheckUser: State<Boolean> = _isSuccessCheckUser

    private val _loadErrorCheckUser = mutableStateOf("")
    val loadErrorCheckUser: State<String> = _loadErrorCheckUser

    private val _isUniqueUser = mutableStateOf(false)
    val isUniqueUser: State<Boolean> = _isUniqueUser

    private val _otp = mutableStateOf("")
    val otp: State<String> = _otp

    fun changeStateUU(bool: Boolean) {
        _isUniqueUser.value = bool
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
                email, user, pwd, phn, reg, dt, lt, lat, lon, ch
            )
//            myPushResponse.value = response
            Log.d("PKDO", response.data?.body().toString())
        }
    }

    fun checkAndRegisterUser(
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
            val result = repository.checkUser(email, phn)
            if (result.data == null) {
                _loadErrorCheckUser.value = result.e.toString()
            } else {
                if (result.data!!.body()!!.success == "1") {
                    _isUniqueUser.value = true
                    _otp.value = result.data!!.body()!!.otp
                } else {
                    _loadErrorCheckUser.value = result.data!!.body()!!.message
                }
            }
        }
    }


}