package controller

import api.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import utils.FileUtils
import utils.Routes

class AppController {

    private val _routes = MutableStateFlow(Routes.LOGIN)
    val routes: StateFlow<Routes> = _routes.asStateFlow()

    private val _preferences = MutableStateFlow(User())
    val preferences: StateFlow<User> = _preferences.asStateFlow()

    private val _arguments = MutableStateFlow<List<String>>(emptyList())
    val arguments: StateFlow<List<String>> = _arguments.asStateFlow()

    fun navigate(routes: Routes, arguments: List<String> = emptyList()) {
        _routes.value = routes
        _arguments.value = arguments
    }

    fun navigateByBottomBar(routes: Routes) {
        if (routes == Routes.LOGIN) {
            FileUtils.clearData("src/main/save/user-cache.txt")
        }

        _routes.value = routes
        _arguments.value = emptyList()
    }

    fun updateAuthToken(newPreferences: User) {
        _preferences.value = newPreferences
    }
}