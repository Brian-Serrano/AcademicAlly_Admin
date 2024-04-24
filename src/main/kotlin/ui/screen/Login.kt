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
import controller.LoginController
import ui.custom_composable.BlackButton
import ui.custom_composable.CustomInputField
import ui.custom_composable.UnAuthScaffold
import ui.theme.typography
import utils.FileUtils
import utils.Routes

@Composable
fun Login(
    api: AcademicallyApi,
    appController: AppController,
    snackBarHostState: SnackbarHostState
) {
    val loginController = remember { LoginController(api) }

    val loginState by loginController.loginState.collectAsState()

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
                text = "LOGIN",
                style = typography.h4
            )
            CustomInputField(
                inputName = "Email",
                input = loginState.email,
                onInputChange = { loginController.updateLoginState(loginState.copy(email = it)) },
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            CustomInputField(
                inputName = "Password",
                input = loginState.password,
                onInputChange = { loginController.updateLoginState(loginState.copy(password = it)) },
                visualTransformation = if (loginState.passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.padding(horizontal = 30.dp),
                trailingIcon = {
                    IconButton(onClick = { loginController.updateLoginState(loginState.copy(passwordVisibility = !loginState.passwordVisibility)) }) {
                        Icon(
                            if (loginState.passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            )
            BlackButton(
                text = "LOG IN",
                action = {
                    loginController.updateLoginState(loginState.copy(buttonEnabled = false))
                    loginController.login(
                        loginState = loginState,
                        success = {
                            FileUtils.write("src/main/save/user-cache.txt", it)
                            appController.updateAuthToken(it)
                            loginController.updateLoginState(loginState.copy(buttonEnabled = true))
                            appController.navigate(Routes.ASSESSMENT)
                            snackBarHostState.showSnackbar("Logged in as admin successfully!")
                        },
                        error = {
                            loginController.updateLoginState(loginState.copy(buttonEnabled = true))
                            snackBarHostState.showSnackbar(it)
                        }
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                enabled = loginState.buttonEnabled
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Text(
                    text = "No account yet?",
                    style = typography.body2,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Signup Now",
                    color = Color.Blue,
                    style = typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { appController.navigate(Routes.SIGNUP) }
                )
            }
        }
    }
}