package controller

import api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.AboutSupportChatState
import utils.Utils

class AboutSupportChatController(private val api: AcademicallyApi) {

    private val _chat = MutableStateFlow(Chat())
    val chat: StateFlow<Chat> = _chat.asStateFlow()

    private val _processState = MutableStateFlow<ProcessState>(ProcessState.Loading)
    val processState: StateFlow<ProcessState> = _processState.asStateFlow()

    private val _chatState = MutableStateFlow(AboutSupportChatState())
    val chatState: StateFlow<AboutSupportChatState> = _chatState.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun updateChatState(newChatState: AboutSupportChatState) {
        _chatState.value = newChatState
    }

    fun getChat(userId: Int) {
        coroutineScope.launch {
            try {
                _processState.value = ProcessState.Loading

                _chat.value = Utils.resourceWrapper(api.getSupportMessageWithUser(userId))

                _processState.value = ProcessState.Success
            } catch (e: Exception) {
                _processState.value = ProcessState.Error(e.message ?: "")
            }
        }
    }

    fun sendMessage(userId: Int, message: String, showSnackBar: suspend (String) -> Unit) {
        coroutineScope.launch {
            try {
                showSnackBar(Utils.resourceWrapper(api.sendSupportMessage(ChatBody(message, 0, userId))).message)
            } catch (e: Exception) {
                showSnackBar(e.message ?: "")
            }
        }
    }

    fun closeMessage(userId: Int, error: suspend (String) -> Unit, success: suspend (String) -> Unit) {
        coroutineScope.launch {
            try {
                val response = Utils.resourceWrapper(api.closeSupportChat(CloseChatBody(userId)))
                success(response.message)
            } catch (e: Exception) {
                error(e.message ?: "")
            }
        }
    }
}