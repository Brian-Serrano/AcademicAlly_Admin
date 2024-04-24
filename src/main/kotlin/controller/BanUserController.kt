package controller

import api.AcademicallyApi
import api.Info
import api.ProcessState
import api.UserBanBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.SearchInfo
import utils.Utils

class BanUserController(private val api: AcademicallyApi) {

    private val _searchInfo = MutableStateFlow(SearchInfo())
    val searchInfo: StateFlow<SearchInfo> = _searchInfo.asStateFlow()

    private val _users = MutableStateFlow<List<Info>>(emptyList())
    val users: StateFlow<List<Info>> = _users.asStateFlow()

    private val _buttonsEnabled = MutableStateFlow<List<Boolean>>(emptyList())
    val buttonsEnabled: StateFlow<List<Boolean>> = _buttonsEnabled.asStateFlow()

    private val _collapsibleEnabled = MutableStateFlow<List<Boolean>>(emptyList())
    val collapsibleEnabled: StateFlow<List<Boolean>> = _collapsibleEnabled.asStateFlow()

    private val _processState = MutableStateFlow<ProcessState>(ProcessState.Success)
    val processState: StateFlow<ProcessState> = _processState.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun updateSearchInfo(newSearch: SearchInfo) {
        _searchInfo.value = newSearch
    }

    private fun updateUsers(newUsers: List<Info>) {
        _users.value = newUsers
    }

    private fun updateButtons(value: Boolean, index: Int) {
        _buttonsEnabled.value = _buttonsEnabled.value.mapIndexed { idx, bool -> if (index == idx) value else bool }
    }

    fun updateCollapsible(value: Boolean, index: Int) {
        _collapsibleEnabled.value = _collapsibleEnabled.value.mapIndexed { idx, bool -> if (index == idx) value else bool }
    }

    fun searchUser(searchQuery: String) {
        coroutineScope.launch {
            try {
                _processState.value = ProcessState.Loading

                updateUsers(Utils.resourceWrapper(api.searchUser(searchQuery)))
                _buttonsEnabled.value = List(_users.value.size) { true }
                _collapsibleEnabled.value = List(_users.value.size) { false }

                _processState.value = ProcessState.Success
            } catch (e: Exception) {
                _processState.value = ProcessState.Error(e.message ?: "")
            }
        }
    }

    fun banUser(value: Boolean, userId: Int, buttonIdx: Int, showSnackBar: suspend (String) -> Unit) {
        coroutineScope.launch {
            updateButtons(false, buttonIdx)

            try {
                showSnackBar(Utils.resourceWrapper(api.banUser(UserBanBody(value, userId))).message)
            } catch (e: Exception) {
                showSnackBar(e.message ?: "")
            }

            updateButtons(true, buttonIdx)
        }
    }
}