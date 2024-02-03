package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import controller.AssessmentController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import api.PendingAssessment
import ui.theme.typography

@Composable
fun Assessment() {
    val assessmentController = remember { AssessmentController() }
    
    val tabs = listOf("Multiple Choice", "Identification", "True or False")

    val tabIndex by assessmentController.tabIndex.collectAsState()
    val multipleChoice by assessmentController.multipleChoice.collectAsState()
    val identification by assessmentController.identification.collectAsState()
    val trueOrFalse by assessmentController.trueOrFalse.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF039BE5)),
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
        when (tabIndex) {
            0 -> AssessmentsMenu(multipleChoice)
            1 -> AssessmentsMenu(identification)
            2 -> AssessmentsMenu(trueOrFalse)
        }
    }
}

@Preview
@Composable
fun AssessmentsMenu(
    assessments: List<PendingAssessment>
) {
    LazyColumn {
        items(items = assessments) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .clickable {  },
                verticalAlignment = Alignment.CenterVertically
            ){
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
            }
        }
    }
}