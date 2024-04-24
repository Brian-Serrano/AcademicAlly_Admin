package ui.custom_composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.theme.typography

@Composable
fun ChatField(
    inputName: String,
    input: String,
    onInputChange: (String) -> Unit,
    onMessageSend: (String) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    TextField(
        value = input,
        onValueChange = onInputChange,
        label = {
            Text(
                text = "Enter ${inputName.lowercase()}",
                style = typography.body2
            )
        },
        placeholder = {
            Text(
                text = "$inputName here",
                style = typography.body2
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = InputFieldColors(),
        modifier = modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(
                onClick = { onMessageSend(input) },
                enabled = enabled
            ) {
                Icon(
                    imageVector = Icons.Default.ChatBubble,
                    contentDescription = null
                )
            }
        }
    )
}