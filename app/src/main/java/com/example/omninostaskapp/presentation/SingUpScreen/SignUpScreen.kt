package com.example.omninostaskapp.presentation.SingUpScreen

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
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.omninostaskapp.presentation.homescreen.UserEvents

@Composable
fun SignUpScreen(
    navController: NavController, viewModel: SignUpScreenViewModel = hiltViewModel()
) {

    val isSuccessCheckUser = viewModel.isSuccessCheckUser.value
    val loadErrorCheckUser = viewModel.loadErrorCheckUser.value

    val isUniqueUser = viewModel.isUniqueUser.value
    val otp = viewModel.otp

    val username = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val phone = remember {
        mutableStateOf("")
    }
    val pass = remember {
        mutableStateOf("")
    }
    val passVisible = remember {
        mutableStateOf(true)
    }

    val cnfPass = remember {
        mutableStateOf("")
    }

    val cnfPassVisible = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current
    val showDialogVerification = remember { mutableStateOf(false) }
    if (isUniqueUser) {

        Toast.makeText(context, "otp is : ${otp.value}", Toast.LENGTH_LONG).show()
        CustomDialogVerification(phone = phone.value, value = "", setShowDialog = {
            showDialogVerification.value = it
        }, setValue = {
            if (it == otp.value) {
                viewModel.registerUser(
                    email = email.value,
                    user = username.value,
                    pwd = cnfPass.value,
                    phn = phone.value,
                    reg = "",
                    dt = "",
                    lt = "",
                    lat = "",
                    lon = "",
                    ch = ""
                )
                Toast.makeText(context, "User Registered !!", Toast.LENGTH_SHORT).show()
                viewModel.changeStateUU(false)
                navController.navigate(AppScreens.WelcomeScreen.name)
            }
        })
    }

    Column(
        modifier = Modifier
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

                OutlinedTextField(value = username.value, leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Person, contentDescription = "person")
                },

                    label = { Text(text = "Username") }, onValueChange = {
                        username.value = it
                    })

                Spacer(modifier = Modifier.size(10.dp))

                OutlinedTextField(value = email.value,
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Email, contentDescription = "email")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = { Text(text = "Email") },
                    onValueChange = {
                        email.value = it
                    })

                Spacer(modifier = Modifier.size(10.dp))

                OutlinedTextField(value = phone.value,
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Phone, contentDescription = "Phone")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    label = { Text(text = "Phone") },
                    onValueChange = {
                        phone.value = it
                    })

                Spacer(modifier = Modifier.size(10.dp))


                OutlinedTextField(value = pass.value,
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Password, contentDescription = "password")
                    },

                    visualTransformation = if (passVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passVisible.value) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (passVisible.value) "Hide password" else "Show password"

                        IconButton(onClick = {
                            passVisible.value = !passVisible.value
                        }) {
                            Icon(imageVector = image, description)
                        }
                    },
                    maxLines = 1,
                    label = { Text(text = "Password") },
                    onValueChange = {
                        pass.value = it
                    })

                Spacer(modifier = Modifier.size(10.dp))


                OutlinedTextField(value = cnfPass.value,
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Password, contentDescription = "password")
                    },

                    visualTransformation = if (cnfPassVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (cnfPassVisible.value) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (cnfPassVisible.value) "Hide password" else "Show password"

                        IconButton(onClick = {
                            cnfPassVisible.value = !cnfPassVisible.value
                        }) {
                            Icon(imageVector = image, description)
                        }
                    },
                    maxLines = 1,
                    label = { Text(text = "Confirm Password") },
                    onValueChange = {
                        cnfPass.value = it
                    })

                Spacer(modifier = Modifier.size(10.dp))

                val context = LocalContext.current
                MyButton(text = "Register", onClick = {

                    if (username.value.isEmpty() || email.value.isEmpty() || phone.value.isEmpty() || pass.value.isEmpty() || cnfPass.value.isEmpty()) {
                        Toast.makeText(context, "Fields are empty", Toast.LENGTH_LONG).show()
                    } else {
                        if (pass.value == cnfPass.value) {
                            viewModel.checkAndRegisterUser(
                                email = email.value,
                                user = username.value,
                                pwd = cnfPass.value,
                                phn = phone.value,
                                reg = "",
                                dt = "",
                                lt = "",
                                lat = "",
                                lon = "",
                                ch = "",
                            )
                        } else {
                            Toast.makeText(context, "Password not same", Toast.LENGTH_LONG).show()
                        }
                    }
                })

            }
        }

        Spacer(modifier = Modifier.size(60.dp))




        Row(
            modifier = Modifier.padding(12.dp),
        ) {

            Text(text = "Have An Account? ")

            Text(text = "Sign In",
                fontSize = 16.sp,
                color = Color.Green,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate(AppScreens.HomeScreen.name)
                })

        }
    }
}