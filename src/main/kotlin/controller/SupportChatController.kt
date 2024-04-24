package controller

import api.AcademicallyApi
import api.Chat
import api.ProcessState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.Utils

class SupportChatController(private val api: AcademicallyApi) {

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    private val _processState = MutableStateFlow<ProcessState>(ProcessState.Loading)
    val processState: StateFlow<ProcessState> = _processState.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun getChats() {
        coroutineScope.launch {
            try {
                _chats.value = Utils.resourceWrapper(api.getSupportMessagesFromAdmin())

                _processState.value = ProcessState.Success
            } catch (e: Exception) {
                _processState.value = ProcessState.Error(e.message ?: "")
            }
        }
    }
}