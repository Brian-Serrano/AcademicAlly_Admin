package controller

import api.AcademicallyApi
import api.AuthResource
import api.SignupBody
import api.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.SignupState

class SignupController(private val api: AcademicallyApi) {

    private val _signupState = MutableStateFlow(SignupState())
    val signupState: StateFlow<SignupState> = _signupState.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun updateSignupState(newSignupState: SignupState) {
        _signupState.value = newSignupState
    }

    fun signup(signupState: SignupState, success: suspend (User) -> Unit, error: suspend (String) -> Unit) {
        coroutineScope.launch {
            try {
                val response = api.adminSignup(
                    SignupBody(
                        signupState.name,
                        signupState.email,
                        signupState.password,
                        signupState.confirmPassword
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