package ui.custom_composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.typography
import utils.SearchInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    placeHolder: String,
    searchInfo: SearchInfo,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onTrailingIconClick: () -> Unit
) {
    SearchBar(
        query = searchInfo.searchQuery,
        onQueryChange = onQueryChange,
        active = searchInfo.isActive,
        onSearch = onSearch,
        onActiveChange = onActiveChange,
        placeholder = {
            Text(
                text = placeHolder,
                style = typography.body1
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            Row {
                if (searchInfo.isActive) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clickable(onClick = onTrailingIconClick)
                    )
                }
            }
        },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = SearchBarDefaults.colors(
            containerColor = Color.White,
            dividerColor = Color.Black,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                disabledTextColor = Color(0xFF000000),
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFFFFFFFF),
                errorContainerColor = Color(0xFFFFFFFF),
                cursorColor = Color(0xFF000000),
                errorCursorColor = Color(0xFF000000),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedLeadingIconColor = Color(0xFF000000),
                unfocusedLeadingIconColor = Color(0xFF000000),
                disabledLeadingIconColor = Color(0xFF000000),
                errorLeadingIconColor = Color(0xFF000000),
                focusedTrailingIconColor = Color(0xFF000000),
                unfocusedTrailingIconColor = Color(0xFF000000),
                disabledTrailingIconColor = Color(0xFF000000),
                errorTrailingIconColor = Color(0xFF000000),
                focusedLabelColor = Color(0xFF000000),
                unfocusedLabelColor = Color(0xFF000000),
                disabledLabelColor = Color(0xFF000000),
                errorLabelColor = Color(0xFF000000),
                focusedPlaceholderColor = Color(0xFF000000),
                unfocusedPlaceholderColor = Color(0xFF000000),
                disabledPlaceholderColor = Color(0xFF000000)
            )
        )
    ) {
        LazyColumn {
            items(items = searchInfo.history) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable { onSearch(it) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = it,
                        style = typography.body1,
                        color = Color.Black
                    )
                }
            }
        }
    }
}