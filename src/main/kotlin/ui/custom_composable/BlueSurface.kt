package ui.custom_composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.typography

@Composable
fun BlueSurface(textOne: String, textTwo: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xFF00BCD4)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = textOne,
            style = typography.caption,
            modifier = Modifier.padding(all = 10.dp)
        )
        Text(
            text = textTwo,
            style = typography.caption,
            modifier = Modifier
                .padding(all = 10.dp)
                .weight(1f)
        )
    }
}