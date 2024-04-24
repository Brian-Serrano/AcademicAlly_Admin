package ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import api.AcademicallyApi
import api.ProcessState
import controller.AboutAssessmentController
import controller.AppController
import ui.custom_composable.*
import ui.theme.typography
import utils.Routes

@Composable
fun AboutAssessment(
    api: AcademicallyApi,
    appController: AppController,
    snackBarHostState: SnackbarHostState,
    type: String,
    assessmentId: Int,
    routes: Routes
) {
    val aboutAssessmentController = remember { AboutAssessmentController(api) }

    LaunchedEffect(Unit) {
        aboutAssessmentController.getData(type, assessmentId)
    }

    val process by aboutAssessmentController.processState.collectAsState()
    val assessmentData by aboutAssessmentController.assessmentData.collectAsState()

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.size(45.dp))
                Text(
                    text = "ASSESSMENT INFORMATION",
                    style = typography.h4,
                    color = Color(0xFF141414),
                    modifier = Modifier.padding(20.dp)
                )
                IconButton(
                    modifier = Modifier.padding(20.dp),
                    onClick = { appController.navigate(Routes.ASSESSMENT) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }

            when (val p = process) {
                is ProcessState.Error -> ErrorComposable(p.message) {
                    aboutAssessmentController.getData(type, assessmentId)
                }
                is ProcessState.Loading -> Loading()
                is ProcessState.Success -> {
                    Row {
                        QuizInfo(name = "Course", value = assessmentData.assessment[0])
                        QuizInfo(name = "Type", value = type)
                        QuizInfo(name = "Module", value = assessmentData.assessment[1])
                        QuizInfo(name = "Creator", value = assessmentData.assessment[2])
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        CustomCard {
                            Column {
                                Text(
                                    text = "QUESTION",
                                    style = typography.subtitle1,
                                    modifier = Modifier.padding(all = 20.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                when (type) {
                                    "Multiple Choice" -> {
                                        val choices = listOf("A", "B", "C", "D")
                                        Text(
                                            text = assessmentData.assessment[4],
                                            style = typography.caption,
                                            modifier = Modifier.padding(all = 20.dp)
                                        )
                                        for (idx in choices.indices) {
                                            BlueSurface(choices[idx], assessmentData.assessment[idx + 5])
                                        }
                                    }
                                    "Identification" -> {
                                        Text(
                                            text = assessmentData.assessment[4],
                                            style = typography.caption,
                                            modifier = Modifier.padding(all = 20.dp)
                                        )
                                    }
                                    "True or False" -> {
                                        Text(
                                            text = assessmentData.assessment[4],
                                            style = typography.caption,
                                            modifier = Modifier.padding(all = 20.dp)
                                        )
                                    }
                                }
                                BlueSurface("Answer", assessmentData.assessment[3])
                            }
                        }
                        Row {
                            GreenButton(
                                action = {
                                    aboutAssessmentController.approveAssessment(
                                        type = type,
                                        assessmentData = assessmentData,
                                        error = snackBarHostState::showSnackbar,
                                        success = {
                                            appController.navigate(Routes.ASSESSMENT)
                                            snackBarHostState.showSnackbar(it)
                                        }
                                    )
                                },
                                text = "APPROVE",
                                enabled = assessmentData.approveButtonEnabled
                            )
                            GreenButton(
                                action = {
                                    aboutAssessmentController.disapproveAssessment(
                                        type = type,
                                        assessmentId = assessmentData.assessmentId,
                                        error = snackBarHostState::showSnackbar,
                                        success = {
                                            appController.navigate(Routes.ASSESSMENT)
                                            snackBarHostState.showSnackbar(it)
                                        }
                                    )
                                },
                                text = "REJECT",
                                enabled = assessmentData.disapproveButtonEnabled
                            )
                        }
                    }
                }
            }
        }
    }
}