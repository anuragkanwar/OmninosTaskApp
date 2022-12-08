package com.example.omninostaskapp.domain.repositories

import android.provider.ContactsContract.Data
import android.util.Log
import com.example.omninostaskapp.data.DataOrException
import com.example.omninostaskapp.data.remote.OmninosApi
import com.example.omninostaskapp.data.remote.dto.ChangePasswordResponse
import com.example.omninostaskapp.data.remote.dto.CheckUserResponse
import com.example.omninostaskapp.data.remote.dto.ForgotPasswordResponse
import com.example.omninostaskapp.data.remote.dto.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: OmninosApi
) {
    suspend fun registerUser(
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
    ): DataOrException<Response<RegisterResponse>, Boolean, Exception> {
        try {
            return DataOrException(
                data = api.registerUser(
                    email, user, pwd, phn, reg, dt, lt, lat, lon, ch
                )
            )
        } catch (e: Exception) {
            Log.d("PKDO", e.toString())
            return DataOrException(e = e)
        }
    }


    suspend fun checkUser(
        email: String, phone: String
    ): DataOrException<Response<CheckUserResponse>, Boolean, Exception> {
        return try {
            val result = api.checkUser(
                email = email, ph = phone
            )
            DataOrException(data = result)

        } catch (e: Exception) {
            Log.d("PKDO", "checkRespoone Error : $e")
            DataOrException(e = e)
        }
    }


    suspend fun forgotPassword(
        phone: String
    ): DataOrException<Response<ForgotPasswordResponse>, Boolean, Exception> {
        return try {
            val result = api.forgetPassword(
                ph = phone
            )
            DataOrException(data = result)

        } catch (e: Exception) {
            Log.d("PKDO", "Forgot Password Error : $e")
            DataOrException(e = e)
        }
    }


    suspend fun changePassword(
        phone: String,
        newPassword: String
    ): DataOrException<Response<ChangePasswordResponse>, Boolean, Exception> {
        return try {
            val result = api.changePassword(
                ph = phone,
                pwd = newPassword
            )
            DataOrException(data = result)

        } catch (e: Exception) {
            Log.d("PKDO", "Forgot Password Error : $e")
            DataOrException(e = e)
        }
    }

}
