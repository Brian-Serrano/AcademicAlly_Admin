package controller

import api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.AssessmentState
import utils.Utils

class AboutAssessmentController(private val api: AcademicallyApi) {

    private val _processState = MutableStateFlow<ProcessState>(ProcessState.Loading)
    val processState: StateFlow<ProcessState> = _processState.asStateFlow()

    private val _assessmentData = MutableStateFlow(AssessmentState())
    val assessmentData: StateFlow<AssessmentState> = _assessmentData.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private fun updateAssessmentData(newState: AssessmentState) {
        _assessmentData.value = newState
    }

    fun getData(type: String, assessmentId: Int) {
        coroutineScope.launch {
            try {
                _processState.value = ProcessState.Loading

                updateAssessmentData(
                    when (type) {
                        "Multiple Choice" -> {
                            val result = Utils.resourceWrapper(api.getPendingMultipleChoiceQuestion(assessmentId))
                            AssessmentState(
                                assessmentId = result.assessmentId,
                                courseId = result.courseId,
                                assessment = listOf(result.courseName, result.module, result.creator, result.answer, result.question, result.letterA, result.letterB, result.letterC, result.letterD),
                                approveButtonEnabled = true,
                                disapproveButtonEnabled = true
                            )
                        }
                        "Identification" -> {
                            val result = Utils.resourceWrapper(api.getPendingIdentificationQuestion(assessmentId))
                            AssessmentState(
                                assessmentId = result.assessmentId,
                                courseId = result.courseId,
                                assessment = listOf(result.courseName, result.module, result.creator, result.answer, result.question),
                                approveButtonEnabled = true,
                                disapproveButtonEnabled = true
                            )
                        }
                        "True or False" -> {
                            val result = Utils.resourceWrapper(api.getPendingTrueOrFalseQuestion(assessmentId))
                            AssessmentState(
                                assessmentId = result.assessmentId,
                                courseId = result.courseId,
                                assessment = listOf(result.courseName, result.module, result.creator, if (result.answer) "TRUE" else "FALSE", result.question),
                                approveButtonEnabled = true,
                                disapproveButtonEnabled = true
                            )
                        }
                        else -> throw IllegalStateException()
                    }
                )

                _processState.value = ProcessState.Success
            } catch (e: Exception) {
                _processState.value = ProcessState.Error(e.message ?: "")
            }
        }
    }

    fun disapproveAssessment(
        type: String,
        assessmentId: Int,
        error: suspend  (String) -> Unit,
        success: suspend (String) -> Unit
    ) {
        coroutineScope.launch {
            try {
                updateAssessmentData(_assessmentData.value.copy(disapproveButtonEnabled = false))

                val response = Utils.resourceWrapper(
                    when (type) {
                        "Multiple Choice" -> {
                            api.disapprovePendingMultipleChoiceAssessment(UnapprovedBody(assessmentId))
                        }
                        "Identification" -> {
                            api.disapprovePendingIdentificationAssessment(UnapprovedBody(assessmentId))
                        }
                        "True or False" -> {
                            api.disapprovePendingTrueOrFalseAssessment(UnapprovedBody(assessmentId))
                        }
                        else -> throw IllegalStateException()
                    }
                )

                updateAssessmentData(_assessmentData.value.copy(disapproveButtonEnabled = true))

                success(response.message)
            } catch (e: Exception) {
                error(e.message ?: "")
                updateAssessmentData(_assessmentData.value.copy(disapproveButtonEnabled = true))
            }
        }
    }

    fun approveAssessment(
        type: String,
        assessmentData: AssessmentState,
        error: suspend (String) -> Unit,
        success: suspend (String) -> Unit
    ) {
        coroutineScope.launch {
            try {
                updateAssessmentData(_assessmentData.value.copy(approveButtonEnabled = false))

                val response = Utils.resourceWrapper(
                    when (type) {
                        "Multiple Choice" -> api.approveMultipleChoiceQuestion(
                            MultipleChoiceBody(
                                assessmentData.assessmentId,
                                assessmentData.courseId,
                                assessmentData.assessment[1],
                                assessmentData.assessment[4],
                                assessmentData.assessment[5],
                                assessmentData.assessment[6],
                                assessmentData.assessment[7],
                                assessmentData.assessment[8],
                                assessmentData.assessment[3],
                                assessmentData.assessment[2]
                            )
                        )
                        "Identification" -> api.approveIdentificationQuestion(
                            IdentificationBody(
                                assessmentData.assessmentId,
                                assessmentData.courseId,
                                assessmentData.assessment[1],
                                assessmentData.assessment[4],
                                assessmentData.assessment[3],
                                assessmentData.assessment[2]
                            )
                        )
                        "True or False" -> api.approveTrueOrFalseQuestion(
                            TrueOrFalseBody(
                                assessmentData.assessmentId,
                                assessmentData.courseId,
                                assessmentData.assessment[1],
                                assessmentData.assessment[4],
                                assessmentData.assessment[3] == "TRUE",
                                assessmentData.assessment[2]
                            )
                        )
                        else -> throw IllegalStateException()
                    }
                )

                updateAssessmentData(_assessmentData.value.copy(approveButtonEnabled = true))

                success(response.message)
            } catch (e: Exception) {
                error(e.message ?: "")
                updateAssessmentData(_assessmentData.value.copy(approveButtonEnabled = true))
            }
        }
    }
}