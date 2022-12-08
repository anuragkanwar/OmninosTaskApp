package com.example.omninostaskapp.presentation.homescreen

import android.R
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChangePasswordDialog(
    valuePass1: String,
    valuePass2: String,
    setShowDialog: (Boolean) -> Unit,
    setValue: (String, String) -> Unit,
    viewModel: DefaultHomeScreenViewModel = hiltViewModel()
) {

    val txtFieldError1 = remember { mutableStateOf("") }
    val txtFieldError2 = remember { mutableStateOf("") }
    val txtField1 = remember { mutableStateOf(valuePass1) }
    val txtField2 = remember { mutableStateOf(valuePass2) }

    val passVisible = remember {
        mutableStateOf(true)
    }


    val cnfPassVisible = remember {
        mutableStateOf(true)
    }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp), color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Change Password", style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(imageVector = Icons.Filled.Cancel,
                            contentDescription = "",
                            tint = colorResource(R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) })
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(
                                width = 2.dp,
                                color = colorResource(id = if (txtFieldError1.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                            ), shape = RoundedCornerShape(50)
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Phone,
                                contentDescription = "",
                                tint = colorResource(R.color.holo_green_light),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        },
                        placeholder = { Text(text = "New Password") },
                        value = txtField1.value,
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
                        onValueChange = {
                            txtField1.value = it
                        })

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(
                                width = 2.dp,
                                color = colorResource(id = if (txtFieldError2.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                            ), shape = RoundedCornerShape(50)
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Phone,
                                contentDescription = "",
                                tint = colorResource(R.color.holo_green_light),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        },
                        placeholder = { Text(text = "Confirm Password") },
                        value = txtField2.value,
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
                        onValueChange = {
                            txtField2.value = it
                        })

                    Spacer(modifier = Modifier.height(20.dp))

                    val context = LocalContext.current
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                if (txtField1.value.isEmpty()) {
                                    txtFieldError1.value = "Field can not be empty"

                                    return@Button
                                }
                                if (txtField2.value.isEmpty()) {
                                    txtFieldError2.value = "Field can not be empty"
                                    return@Button
                                }

                                if (txtField1.value != txtField2.value) {
                                    txtFieldError2.value = "Password Not Same"
                                    return@Button
                                } else {
                                    Toast.makeText(
                                        context, "Password Changed Successfully", Toast.LENGTH_SHORT
                                    ).show()
                                    setValue(txtField1.value, txtField2.value)
                                    setShowDialog(false)
                                }
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }
    }
}