/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.Screens
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            Image(
                painter = painterResource(id = R.drawable.login_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Text(
                stringResource(id = R.string.title_welcome),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.firstBaselineToTop(152.dp)
            )
        }
        Column(modifier = Modifier.padding(top = 40.dp, start = 16.dp, end = 16.dp)) {

            val (email, onEmailChange) = remember { mutableStateOf("") }
            val (password, onPasswordChange) = remember { mutableStateOf("") }

            LoginTextField(
                text = email,
                onValueChange = onEmailChange,
                placeholder = stringResource(id = R.string.placeholder_email),
                icon = Icons.Default.MailOutline,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
            )

            LoginTextField(
                text = password,
                onValueChange = onPasswordChange,
                placeholder = stringResource(id = R.string.placeholder_password),
                icon = Icons.Default.Password,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            PrimaryButton(
                text = stringResource(id = R.string.btn_log_in),
                onClick = { navController.navigate(Screens.Home.route) },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginTextField(
    text: String,
    onValueChange: (value: String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {}
) {
    Row(
        modifier
            .height(56.dp)
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colors.onSurface),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(start = 16.dp)
    ) {
        val textColor = MaterialTheme.colors.onSurface
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp)
                .size(24.dp),
            tint = textColor

        )
        val isFocused = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {

            val keyboardController = LocalSoftwareKeyboardController.current

            if (text.isEmpty() && !isFocused.value) {
                Text(text = placeholder, color = textColor)
            }

            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                cursorBrush = SolidColor(textColor),
                textStyle = MaterialTheme.typography.body1.copy(color = textColor),
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onImeAction()
                        keyboardController?.hideSoftwareKeyboard()
                    }
                ),
                visualTransformation = visualTransformation,
                singleLine = true,
                modifier = Modifier
                    .firstBaselineToTop(12.dp)
                    .fillMaxWidth()
                    .onFocusChanged { state -> isFocused.value = state.isFocused }
            )
        }
    }
}

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = Modifier.layout { measurable, constraints ->
    // Measure the composable
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.place(0, placeableY)
    }
}
