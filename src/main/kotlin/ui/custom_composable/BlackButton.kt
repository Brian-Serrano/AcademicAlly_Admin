package ui.custom_composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
fun BlackButton(
    text: String,
    action: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = typography.subtitle1,
    enabled: Boolean = true
) {
    Button(
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF000000),
            contentColor = Color(0xFFFFFFFF),
            disabledBackgroundColor = Color(0xFF000000),
            disabledContentColor = Color(0xFFFFFFFF)
        ),
        modifier = modifier,
        shape = RoundedCornerShape(30.dp)
    ) {
        Box(
            modifier = Modifier.height(if (style == typography.subtitle1) 30.dp else 25.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = style,
                color = if (enabled) Color(0xFFFFFFFF) else Color.Transparent
            )
            if (!enabled) {
                CircularProgressIndicator(color = Color(0xFFFFFFFF), modifier = Modifier.size(20.dp))
            }
        }
    }
}