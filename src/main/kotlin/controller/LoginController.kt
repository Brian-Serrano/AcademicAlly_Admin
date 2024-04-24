package controller

import api.AcademicallyApi
import api.AuthResource
import api.LoginBody
import api.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.LoginState

class LoginController(private val api: AcademicallyApi) {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun updateLoginState(newLoginState: LoginState) {
        _loginState.value = newLoginState
    }

    fun login(loginState: LoginState, success: suspend (User) -> Unit, error: suspend (String) -> Unit) {
        coroutineScope.launch {
            try {
                val response = api.adminLogin(
                    LoginBody(
                        loginState.email,
                        loginState.password
                    )
                )
                when (response) {
                    is AuthResource.Success -> success(response.data!!)
                    is AuthResource.ValidationError -> error(response.message ?: "")
                    is AuthResource.Error -> error(response.error ?: "")
                }
            } catch (e: Exception) {
                error(e.message ?: "")
            }
        }
    }
}