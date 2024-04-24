package ui.custom_composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.typography

@Composable
fun RowScope.QuizInfo(name: String, value: String) {
    Card(
        backgroundColor = Color(0xFFFFFFCC),
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
            .weight(1f)
            .height(150.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(
                text = name,
                style = typography.caption,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )
            Text(
                text = value,
                style = typography.caption,
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
            )
        }
    }
}