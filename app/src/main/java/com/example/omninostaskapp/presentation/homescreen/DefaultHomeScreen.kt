package com.example.omninostaskapp.presentation.homescreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.omninostaskapp.R
import com.example.omninostaskapp.navigation.AppScreens
import com.example.omninostaskapp.presentation.MyButton
import com.example.omninostaskapp.presentation.TransparentHintTextField


@Composable
fun DefaultHomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DefaultHomeScreenViewModel = hiltViewModel()
) {

    val emailState = viewModel.userEmail.value
    val passwordState = viewModel.userPassword.value
    val forgotPhone = viewModel.userForgotPasswordPhone.value
    val isSuccessForgotPassword = viewModel.isSuccessForgotPassword.value
    val loadErrorForgotPassword = viewModel.loadErrorForgotPassword.value
    val isSuccessChangePassword = viewModel.isSuccessChangePassword.value
    val loadErrorChangePassword = viewModel.loadErrorChangePassword.value

    var phoneNumber = ""
    val showDialogForgotPassword = remember { mutableStateOf(false) }
    val showDialogChangePassword = remember { mutableStateOf(false) }


    if (showDialogForgotPassword.value) {
        CustomDialogForgotPassword(value = "", setShowDialog = {
            showDialogForgotPassword.value = it
        }, setValue = {
            phoneNumber = it
            viewModel.forgotPassword(it)
        })
    }

    if (isSuccessForgotPassword) {
        showDialogChangePassword.value = true
        viewModel.changeForgotPasswordState(false)
    }

    if (showDialogChangePassword.value) {
        ChangePasswordDialog(valuePass1 = "", valuePass2 = "", setShowDialog = {
            showDialogChangePassword.value = it
        }, setValue = { nwPass, confirmPass ->
            viewModel.changePassword(phoneNumber, confirmPass)
        })
    }

    if (loadErrorForgotPassword.isNotEmpty()) {
        Toast.makeText(LocalContext.current, loadErrorForgotPassword, Toast.LENGTH_SHORT).show()
    }
    if (loadErrorChangePassword.isNotEmpty()) {
        Toast.makeText(LocalContext.current, loadErrorChangePassword, Toast.LENGTH_SHORT).show()
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(60.dp))

        Image(
            painter = painterResource(id = R.drawable.svglogo),
            contentDescription = "logo",
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
        )

        Spacer(modifier = Modifier.size(60.dp))

        Card(
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                OutlinedTextField(value = emailState.text, leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Email, contentDescription = "email")
                },

                    label = { Text(text = "Email") }, onValueChange = {
                        viewModel.onEvent(UserEvents.EnteredEmail(it))
                    })

                Spacer(modifier = Modifier.size(20.dp))


                OutlinedTextField(value = passwordState.text,
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Password, contentDescription = "password")
                    },

                    visualTransformation = if (passwordState.passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordState.passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (passwordState.passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {
                            viewModel.onEvent(UserEvents.ChangePasswordVisibility)
                        }) {
                            Icon(imageVector = image, description)
                        }
                    },
                    maxLines = 1,
                    label = { Text(text = "Password") },
                    onValueChange = {
                        viewModel.onEvent(UserEvents.EnteredPassword(it))
                    })

                Spacer(modifier = Modifier.size(10.dp))
                val context = LocalContext.current
                MyButton(text = "LOGIN", onClick = {
                    Toast.makeText(context, "Login Button Clicked", Toast.LENGTH_SHORT).show()
                    navController.navigate(AppScreens.WelcomeScreen.name)
                })

            }
        }

        Spacer(modifier = Modifier.size(60.dp))




        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(text = "Forgot Password ? ", modifier = Modifier.clickable {
                showDialogForgotPassword.value = true
            })

            Text(text = "Register",
                fontSize = 16.sp,
                color = Color.Green,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate(AppScreens.SignUpScreen.name)
                })

        }
    }
}
//) {
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceEvenly,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        Button(onClick = {
//            viewModel.registerUser(
//                email = "testemail14@gmail.com",
//                user = "username",
//                pwd = "password",
//                phn = "8967452310",
//                reg = "12345",
//                dt = "xiaomi",
//                lt = "mobile",
//                lat = "12.345",
//                lon = "12.345",
//                ch = "12345"
//            )
//        }) {
//            Text("Click")
//        }
//
//    }
//}

//@SuppressLint("HardwareIds")
//private fun getSystemDetail(): String {
//    return "Brand: ${Build.BRAND} \n" +
////            "DeviceID: ${
////                Settings.Secure.getString(
////                    contentResolver,
////                    Settings.Secure.ANDROID_ID
////                )
////            } \n" +
//            "Model: ${Build.MODEL} \n" +
//            "ID: ${Build.ID} \n" +
//            "SDK: ${Build.VERSION.SDK_INT} \n" +
//            "Manufacture: ${Build.MANUFACTURER} \n" +
//            "Brand: ${Build.BRAND} \n" +
//            "User: ${Build.USER} \n" +
//            "Type: ${Build.TYPE} \n" +
//            "Base: ${Build.VERSION_CODES.BASE} \n" +
//            "Incremental: ${Build.VERSION.INCREMENTAL} \n" +
//            "Board: ${Build.BOARD} \n" +
//            "Host: ${Build.HOST} \n" +
//            "FingerPrint: ${Build.FINGERPRINT} \n" +
//            "Version Code: ${Build.VERSION.RELEASE}"
//}



