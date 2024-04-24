package ui.custom_composable

import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun InputFieldColors(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        textColor = Color(0xFF000000),
        disabledTextColor = Color(0xFF000000),
        backgroundColor = Color(0xFFFFFFFF),
        cursorColor = Color(0xFF000000),
        errorCursorColor = Color(0xFF000000),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        leadingIconColor = Color(0xFF000000),
        disabledLeadingIconColor = Color(0xFF000000),
        errorLeadingIconColor = Color(0xFF000000),
        trailingIconColor = Color(0xFF000000),
        disabledTrailingIconColor = Color(0xFF000000),
        errorTrailingIconColor = Color(0xFF000000),
        focusedLabelColor = Color(0xFF000000),
        unfocusedLabelColor = Color(0xFF000000),
        disabledLabelColor = Color(0xFF000000),
        errorLabelColor = Color(0xFF000000),
        placeholderColor = Color(0xFF000000),
        disabledPlaceholderColor = Color(0xFF000000)
    )
}