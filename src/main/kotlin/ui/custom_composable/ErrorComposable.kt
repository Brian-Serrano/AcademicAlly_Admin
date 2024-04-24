package ui.custom_composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.typography

@Composable
fun ErrorComposable(
    message: String,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF039BE5)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No Internet Connection",
                style = typography.h4,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = message,
                style = typography.body1,
                modifier = Modifier.padding(10.dp)
            )
            BlackButton(
                text = "Refresh",
                action = onRefresh,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}