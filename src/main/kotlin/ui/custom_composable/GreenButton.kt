package ui.custom_composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ui.theme.typography

@Composable
fun RowScope.GreenButton(
    action: () -> Unit,
    text: String,
    style: TextStyle = typography.caption,
    enabled: Boolean = true
) {
    Button(
        onClick = action,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4CAF50),
            contentColor = Color(0xFFFFFFFF),
            disabledBackgroundColor = Color(0xFF4CAF50),
            disabledContentColor = Color(0xFFFFFFFF)
        ),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .weight(1f),
        enabled = enabled
    ) {
        Box(
            modifier = Modifier.height(25.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (enabled) Color(0xFFFFFFFF) else Color.Transparent,
                style = style,
                maxLines = 1
            )
            if (!enabled) {
                CircularProgressIndicator(color = Color(0xFFFFFFFF), modifier = Modifier.size(20.dp))
            }
        }
    }
}