package ui.screen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import api.AcademicallyApi
import api.ProcessState
import controller.AboutSupportChatController
import controller.AppController
import ui.custom_composable.*
import ui.theme.typography
import utils.Routes
import utils.Utils

@Composable
fun AboutSupportChat(
    api: AcademicallyApi,
    appController: AppController,
    snackBarHostState: SnackbarHostState,
    userId: Int,
    routes: Routes
) {
    val aboutSupportChatController = remember { AboutSupportChatController(api) }

    LaunchedEffect(Unit) {
        aboutSupportChatController.getChat(userId)
    }

    val chat by aboutSupportChatController.chat.collectAsState()
    val process by aboutSupportChatController.processState.collectAsState()
    val chatState by aboutSupportChatController.chatState.collectAsState()

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
            when (val p = process) {
                is ProcessState.Error -> ErrorComposable(p.message) {
                    aboutSupportChatController.getChat(userId)
                }
                is ProcessState.Loading -> Loading()
                is ProcessState.Success -> {
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
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${chat.userName} - ${chat.userRole}",
                                            style = typography.h6,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                        IconButton(
                                            modifier = Modifier.padding(20.dp),
                                            onClick = { appController.navigate(Routes.SUPPORT_CHAT) }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowBack,
                                                contentDescription = null,
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                    Text(
                                        text = chat.userEmail,
                                        style = typography.body1,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                    BlackButton(
                                        text = "CLOSE MESSAGE",
                                        action = {
                                            aboutSupportChatController.updateChatState(chatState.copy(closeButtonEnabled = false))

                                            aboutSupportChatController.closeMessage(
                                                userId = chat.userId,
                                                error = {
                                                    aboutSupportChatController.updateChatState(chatState.copy(closeButtonEnabled = true))
                                                    snackBarHostState.showSnackbar(it)
                                                },
                                                success = {
                                                    aboutSupportChatController.updateChatState(chatState.copy(closeButtonEnabled = true))
                                                    appController.navigate(Routes.SUPPORT_CHAT)
                                                    snackBarHostState.showSnackbar("Successfully closed chat.")
                                                }
                                            )
                                        },
                                        modifier = Modifier.padding(10.dp).align(Alignment.End),
                                        enabled = chatState.closeButtonEnabled
                                    )
                                }
                            }
                        }
                    }
                    LazyColumn {
                        items(items = chat.messages) {
                            Column(modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                            ) {
                                if (it.isSender) {
                                    Text(text = chat.userName, modifier = Modifier.padding(horizontal = 10.dp))
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (!it.isSender) Color(0xFF4CAF50) else Color(0xFF00BCD4))
                                        .fillMaxWidth(0.8f)
                                        .padding(10.dp)
                                        .align(if (!it.isSender) Alignment.End else Alignment.Start)
                                ) {
                                    Text(
                                        text = it.message,
                                        style = typography.body1,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = if (!it.isSender) TextAlign.Right else TextAlign.Left
                                    )
                                }

                                Text(
                                    text = Utils.formatDate(Utils.convertToDate(it.sentDate)),
                                    style = typography.body2,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .fillMaxWidth(),
                                    textAlign = if (!it.isSender) TextAlign.Right else TextAlign.Left
                                )
                            }
                        }
                        item {
                            ChatField(
                                inputName = "Send Message",
                                input = chatState.message,
                                onInputChange = { aboutSupportChatController.updateChatState(chatState.copy(message = it)) },
                                onMessageSend = {
                                    aboutSupportChatController.updateChatState(chatState.copy(sendButtonEnabled = false))
                                    aboutSupportChatController.sendMessage(chat.userId, chatState.message) {
                                        snackBarHostState.showSnackbar(it)
                                        aboutSupportChatController.updateChatState(chatState.copy(sendButtonEnabled = true))
                                    }
                                },
                                enabled = chatState.sendButtonEnabled,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}