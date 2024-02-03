package controller

import api.PendingAssessment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AssessmentController {

    private val _tabIndex = MutableStateFlow(0)
    val tabIndex: StateFlow<Int> = _tabIndex.asStateFlow()

    private val _multipleChoice = MutableStateFlow<List<PendingAssessment>>(emptyList())
    val multipleChoice: StateFlow<List<PendingAssessment>> = _multipleChoice.asStateFlow()

    private val _identification = MutableStateFlow<List<PendingAssessment>>(emptyList())
    val identification: StateFlow<List<PendingAssessment>> = _identification.asStateFlow()

    private val _trueOrFalse = MutableStateFlow<List<PendingAssessment>>(emptyList())
    val trueOrFalse: StateFlow<List<PendingAssessment>> = _trueOrFalse.asStateFlow()

    fun updateTabIndex(newIdx: Int) {
        _tabIndex.value = newIdx
    }
}