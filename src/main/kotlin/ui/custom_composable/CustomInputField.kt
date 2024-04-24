package ui.custom_composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ui.theme.typography

@Composable
fun CustomInputField(
    inputName: String,
    input: String,
    onInputChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = input,
        onValueChange = onInputChange,
        label = {
            Text(
                text = "Enter ${inputName.lowercase()}",
                style = typography.body1
            )
        },
        placeholder = {
            Text(
                text = "$inputName here",
                style = typography.body1
            )
        },
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        minLines = minLines,
        maxLines = maxLines,
        shape = RoundedCornerShape(30.dp),
        colors = InputFieldColors(),
        modifier = modifier.fillMaxWidth(),
        trailingIcon = trailingIcon
    )
}