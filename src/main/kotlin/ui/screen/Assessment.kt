package ui.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import controller.AssessmentController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import api.AcademicallyApi
import api.PendingAssessment
import api.ProcessState
import controller.AppController
import ui.custom_composable.*
import ui.theme.typography
import utils.Routes

@Composable
fun Assessment(
    api: AcademicallyApi,
    appController: AppController,
    snackBarHostState: SnackbarHostState,
    routes: Routes
) {
    val assessmentController = remember { AssessmentController(api) }
    
    val tabs = listOf("Multiple Choice", "Identification", "True or False")

    LaunchedEffect(Unit) {
        assessmentController.getData()
    }

    val tabIndex by assessmentController.tabIndex.collectAsState()
    val multipleChoice by assessmentController.multipleChoice.collectAsState()
    val identification by assessmentController.identification.collectAsState()
    val trueOrFalse by assessmentController.trueOrFalse.collectAsState()
    val process by assessmentController.processState.collectAsState()

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
            TabRow(
                selectedTabIndex = tabIndex,
                backgroundColor = Color(0xFFFFEB3B),
                contentColor = Color(0xFF000000)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                style = typography.body1
                            )
                        },
                        selected = tabIndex == index,
                        onClick = { assessmentController.updateTabIndex(index) }
                    )
                }
            }
            when (val p = process) {
                is ProcessState.Error -> ErrorComposable(p.message, assessmentController::getData)
                is ProcessState.Loading -> Loading()
                is ProcessState.Success -> {
                    when (tabIndex) {
                        0 -> AssessmentsMenu(multipleChoice, appController, "Multiple Choice")
                        1 -> AssessmentsMenu(identification, appController, "Identification")
                        2 -> AssessmentsMenu(trueOrFalse, appController, "True or False")
                    }
                }
            }
        }
    }
}

@Composable
fun AssessmentsMenu(
    assessments: List<PendingAssessment>,
    appController: AppController,
    type: String
) {
    LazyColumn {
        items(items = assessments) {
            CustomCard {
                Row(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = it.creator,
                            style = typography.caption,
                            modifier = Modifier.padding(start = 5.dp, top = 5.dp)
                        )
                        Text(
                            text = it.courseName,
                            style = typography.body1,
                            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
                        )
                        Text(
                            text = it.module,
                            style = typography.body1,
                            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
                        )
                    }
                    BlackButton(
                        text = "VIEW",
                        action = { appController.navigate(Routes.ABOUT_ASSESSMENT, listOf(type, it.assessmentId.toString())) }
                    )
                }
            }
        }
    }
}