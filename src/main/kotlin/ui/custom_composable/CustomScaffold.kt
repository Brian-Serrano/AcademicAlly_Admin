package ui.custom_composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ui.theme.typography
import utils.Routes

@Composable
fun CustomScaffold(
    snackBarHostState: SnackbarHostState,
    routes: Routes,
    onNavigate: (Routes) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFFFFFCC),
                contentColor = Color(0xFF141414)
            ) {
                val icons = listOf(
                    listOf(Icons.Outlined.Assessment, Icons.Filled.Assessment),
                    listOf(Icons.Outlined.Chat, Icons.Filled.Chat),
                    listOf(Icons.Outlined.PersonRemove, Icons.Filled.PersonRemove),
                    listOf(Icons.Outlined.Logout, Icons.Filled.Logout)
                )

                listOf(
                    "ASSESSMENTS" to Routes.ASSESSMENT,
                    "USER CHAT" to Routes.SUPPORT_CHAT,
                    "BAN USER" to Routes.BAN_USER,
                    "LOG OUT" to Routes.LOGIN
                ).forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = routes == item.second,
                        onClick = { onNavigate(item.second) },
                        label = {
                            Text(
                                text = item.first,
                                style = typography.body1
                            )
                        },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                imageVector = icons[index][if (routes == item.second) 1 else 0],
                                contentDescription = null
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF000000),
                            selectedTextColor = Color(0xFF000000),
                            indicatorColor = Color.LightGray,
                            unselectedIconColor = Color(0xFF141414),
                            unselectedTextColor = Color(0xFF141414),
                            disabledIconColor = Color.Transparent,
                            disabledTextColor = Color.Transparent
                        )
                    )
                }
            }
        },
        content = content
    )
}