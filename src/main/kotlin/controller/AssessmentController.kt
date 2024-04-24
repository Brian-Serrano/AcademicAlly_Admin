package controller

import api.AcademicallyApi
import api.PendingAssessment
import api.ProcessState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.Utils

class AssessmentController(private val api: AcademicallyApi) {

    private val _tabIndex = MutableStateFlow(0)
    val tabIndex: StateFlow<Int> = _tabIndex.asStateFlow()

    private val _multipleChoice = MutableStateFlow<List<PendingAssessment>>(emptyList())
    val multipleChoice: StateFlow<List<PendingAssessment>> = _multipleChoice.asStateFlow()

    private val _identification = MutableStateFlow<List<PendingAssessment>>(emptyList())
    val identification: StateFlow<List<PendingAssessment>> = _identification.asStateFlow()

    private val _trueOrFalse = MutableStateFlow<List<PendingAssessment>>(emptyList())
    val trueOrFalse: StateFlow<List<PendingAssessment>> = _trueOrFalse.asStateFlow()

    private val _processState = MutableStateFlow<ProcessState>(ProcessState.Loading)
    val processState: StateFlow<ProcessState> = _processState.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun updateTabIndex(newIdx: Int) {
        _tabIndex.value = newIdx
    }

    fun getData() {
        coroutineScope.launch {
            try {
                _processState.value = ProcessState.Loading

                _multipleChoice.value = Utils.resourceWrapper(api.getPendingMultipleChoiceQuestions())
                _identification.value = Utils.resourceWrapper(api.getPendingIdentificationQuestions())
                _trueOrFalse.value = Utils.resourceWrapper(api.getPendingTrueOrFalseQuestions())

                _processState.value = ProcessState.Success
            } catch (e: Exception) {
                _processState.value = ProcessState.Error(e.message ?: "")
            }
        }
    }
}