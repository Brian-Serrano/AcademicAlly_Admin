import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import api.*
import controller.AppController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ui.custom_composable.UnAuthScaffold
import ui.screen.*
import ui.theme.typography
import utils.FileUtils
import utils.Routes

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AcademicAlly Admin",
        state = WindowState(size = DpSize(width = 1080.dp, height = 720.dp))
    ) {
        val appController = remember { AppController() }

        LaunchedEffect(Unit) {
            val fileData = FileUtils.read("src/main/save/user-cache.txt")
            appController.updateAuthToken(fileData)

            if (fileData != User()) {
                appController.navigate(Routes.ASSESSMENT)
            }
        }

        val routes by appController.routes.collectAsState()
        val preferences by appController.preferences.collectAsState()
        val arguments by appController.arguments.collectAsState()

        val snackBarHostState = remember { SnackbarHostState() }

        val api = AcademicallyApi.getInstance(preferences)

        when (routes) {
            Routes.LOGIN -> {
                Login(api, appController, snackBarHostState)
            }
            Routes.SIGNUP -> {
                Signup(api, appController, snackBarHostState)
            }
            Routes.ASSESSMENT -> {
                Assessment(api, appController, snackBarHostState, routes)
            }
            Routes.ABOUT_ASSESSMENT -> {
                AboutAssessment(api, appController, snackBarHostState, arguments[0], arguments[1].toInt(), routes)
            }
            Routes.BAN_USER -> {
                BanUser(api, appController, snackBarHostState, routes)
            }
            Routes.SUPPORT_CHAT -> {
                SupportChat(api, appController, snackBarHostState, routes)
            }
            Routes.ABOUT_SUPPORT_CHAT -> {
                AboutSupportChat(api, appController, snackBarHostState, arguments[0].toInt(), routes)
            }
        }
    }
}
