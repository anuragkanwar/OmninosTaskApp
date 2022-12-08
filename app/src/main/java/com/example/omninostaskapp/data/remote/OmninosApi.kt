package com.example.omninostaskapp.data.remote

import com.example.omninostaskapp.data.remote.dto.ChangePasswordResponse
import com.example.omninostaskapp.data.remote.dto.CheckUserResponse
import com.example.omninostaskapp.data.remote.dto.ForgotPasswordResponse
import com.example.omninostaskapp.data.remote.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface OmninosApi {

    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("username") user: String,
        @Field("password") pwd: String,
        @Field("phone") phn: String,
        @Field("reg_id") reg: String,
        @Field("device_type") dt: String,
        @Field("login_type") lt: String,
        @Field("latitude") lat: String,
        @Field("longitude") lon: String,
        @Field("chatId") ch: String
    ): Response<RegisterResponse>


//    @FormUrlEncoded
//    @POST("userLogin")
//    suspend fun loginUser(
//        @Field("email") email: String,
//        @Field("password") pwd: String,
//        @Field("reg_id") reg: String,
//        @Field("device_type") dt: String,
//        @Field("login_type") lt: String,
//        @Field("latitude") lat: String,
//        @Field("longitude") lon: String,
//    ): Response<RegisterResponse>


    @FormUrlEncoded
    @POST("uniqueAPI")
    suspend fun checkUser(
        @Field("email") email: String,
        @Field("phone") ph: String
    ): Response<CheckUserResponse>

    @FormUrlEncoded
    @POST("forgetPassword")
    suspend fun forgetPassword(
        @Field("number") ph: String
    ): Response<ForgotPasswordResponse>

    @FormUrlEncoded
    @POST("changePassword")
    suspend fun changePassword(
        @Field("phone") ph: String,
        @Field("password") pwd: String
    ): Response<ChangePasswordResponse>

}