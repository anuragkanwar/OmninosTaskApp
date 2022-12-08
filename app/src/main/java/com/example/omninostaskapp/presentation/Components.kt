package com.example.omninostaskapp.presentation

import android.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omninostaskapp.presentation.SingUpScreen.SignUpScreenViewModel
import com.example.omninostaskapp.presentation.homescreen.DefaultHomeScreenViewModel
import java.util.*


@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        enabled = enabled,
        modifier = modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Text(text = text, modifier = Modifier.padding(6.dp))
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    onPasswordChange: () -> Unit = {},
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(16.dp)
                .onFocusChanged {
                    onFocusChange(it)
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone =
            {
                keyboardController?.hide()
            })
        )
        if (isHintVisible) {
            Text(
                text = hint,
                style = textStyle,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart)
            )
        }
        if (isPasswordField) {
            val image = if (isPasswordVisible)
                Icons.Outlined.Visibility
            else Icons.Outlined.VisibilityOff
            val description = if (isPasswordVisible) "Hide password" else "Show password"
            Box(
                modifier = Modifier.align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            )
            {
                IconButton(onClick = { onPasswordChange() }) {
                    Icon(
                        imageVector = image,
                        description,
                        tint = Color.Black,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(end = 8.dp)
                    )
                }
            }
        }
    }
}
