package ui.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import api.AcademicallyApi
import api.ProcessState
import controller.AppController
import controller.SupportChatController
import ui.custom_composable.*
import ui.theme.typography
import utils.Routes
import utils.Utils

@Composable
fun SupportChat(
    api: AcademicallyApi,
    appController: AppController,
    snackBarHostState: SnackbarHostState,
    routes: Routes
) {
    val supportChatController = remember { SupportChatController(api) }

    LaunchedEffect(Unit) {
        supportChatController.getChats()
    }

    val chats by supportChatController.chats.collectAsState()
    val process by supportChatController.processState.collectAsState()

    CustomScaffold(
        snackBarHostState = snackBarHostState,
        routes = routes,
        onNavigate = appController::navigateByBottomBar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF039BE5))
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MESSAGES",
                style = typography.h4,
                color = Color(0xFF141414),
                modifier = Modifier.padding(20.dp)
            )

            when (val p = process) {
                is ProcessState.Error -> ErrorComposable(p.message, supportChatController::getChats)
                is ProcessState.Loading -> Loading()
                is ProcessState.Success -> {
                    LazyColumn {
                        items(items = chats) { chat ->
                            CustomCard {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            bitmap = Utils.convertToImage(chat.userImage),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(10.dp)
                                                .clip(RoundedCornerShape(50.dp))
                                        )
                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            val modifiedMessage = chat.messages
                                                .sortedByDescending { Utils.convertToDate(it.sentDate) }
                                                .take(3)

                                            Text(
                                                text = "${chat.userName} - ${chat.userRole}",
                                                style = typography.h6,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                            Text(
                                                text = chat.userEmail,
                                                style = typography.body1,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                            modifiedMessage.forEach {
                                                Text(
                                                    text = buildAnnotatedString {
                                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                                            append("${if (it.isSender) chat.userName else "Admin"} ")
                                                        }
                                                        append(it.message)
                                                    },
                                                    style = typography.body2,
                                                    modifier = Modifier.padding(5.dp)
                                                )
                                            }
                                            BlackButton(
                                                text = "MESSAGE",
                                                action = { appController.navigate(Routes.ABOUT_SUPPORT_CHAT, listOf(chat.userId.toString())) },
                                                modifier = Modifier.padding(10.dp).align(Alignment.End)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}