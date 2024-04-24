package ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import api.AcademicallyApi
import controller.AppController
import controller.SignupController
import ui.custom_composable.BlackButton
import ui.custom_composable.CustomInputField
import ui.custom_composable.UnAuthScaffold
import ui.theme.typography
import utils.FileUtils
import utils.Routes

@Composable
fun Signup(
    api: AcademicallyApi,
    appController: AppController,
    snackBarHostState: SnackbarHostState
) {
    val signupController = remember { SignupController(api) }

    val signupState by signupController.signupState.collectAsState()

    UnAuthScaffold(snackBarHostState) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF039BE5))
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "SIGNUP",
                style = typography.h4
            )
            CustomInputField(
                inputName = "Name",
                input = signupState.name,
                onInputChange = { signupController.updateSignupState(signupState.copy(name = it)) },
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            CustomInputField(
                inputName = "Email",
                input = signupState.email,
                onInputChange = { signupController.updateSignupState(signupState.copy(email = it)) },
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            CustomInputField(
                inputName = "Password",
                input = signupState.password,
                onInputChange = { signupController.updateSignupState(signupState.copy(password = it)) },
                visualTransformation = if (signupState.passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.padding(horizontal = 30.dp),
                trailingIcon = {
                    IconButton(onClick = { signupController.updateSignupState(signupState.copy(passwordVisibility = !signupState.passwordVisibility)) }) {
                        Icon(
                            if (signupState.passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            )
            CustomInputField(
                inputName = "Confirm Password",
                input = signupState.confirmPassword,
                onInputChange = { signupController.updateSignupState(signupState.copy(confirmPassword = it)) },
                visualTransformation = if (signupState.confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.padding(horizontal = 30.dp),
                trailingIcon = {
                    IconButton(onClick = { signupController.updateSignupState(signupState.copy(confirmPasswordVisibility = !signupState.confirmPasswordVisibility)) }) {
                        Icon(
                            if (signupState.confirmPasswordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            )
            BlackButton(
                text = "SIGN UP",
                action = {
                    signupController.updateSignupState(signupState.copy(buttonEnabled = false))
                    signupController.signup(
                        signupState = signupState,
                        success = {
                            FileUtils.write("src/main/save/user-cache.txt", it)
                            appController.updateAuthToken(it)
                            signupController.updateSignupState(signupState.copy(buttonEnabled = true))
                            appController.navigate(Routes.ASSESSMENT)
                            snackBarHostState.showSnackbar("Signed up as admin successfully!")
                        },
                        error = {
                            signupController.updateSignupState(signupState.copy(buttonEnabled = true))
                            snackBarHostState.showSnackbar(it)
                        }
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                enabled = signupState.buttonEnabled
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Text(
                    text = "Already have account?",
                    style = typography.body2,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Login Now",
                    color = Color.Blue,
                    style = typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { appController.navigate(Routes.LOGIN) }
                )
            }
        }
    }
}