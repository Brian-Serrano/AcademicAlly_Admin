package ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import api.AcademicallyApi
import api.Info
import api.ProcessState
import controller.AppController
import controller.BanUserController
import ui.custom_composable.*
import ui.theme.typography
import utils.Routes
import utils.Utils

@Composable
fun BanUser(
    api: AcademicallyApi,
    appController: AppController,
    snackBarHostState: SnackbarHostState,
    routes: Routes
) {
    val banUserController = remember { BanUserController(api) }

    val searchInfo by banUserController.searchInfo.collectAsState()
    val process by banUserController.processState.collectAsState()
    val users by banUserController.users.collectAsState()
    val buttonsEnabled by banUserController.buttonsEnabled.collectAsState()
    val collapsibleEnabled by banUserController.collapsibleEnabled.collectAsState()

    CustomScaffold(
        snackBarHostState = snackBarHostState,
        routes = routes,
        onNavigate = appController::navigateByBottomBar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF039BE5))
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomSearchBar(
                placeHolder = "Search User",
                searchInfo = searchInfo,
                onQueryChange = { banUserController.updateSearchInfo(searchInfo.copy(searchQuery = it)) },
                onSearch = {
                    banUserController.updateSearchInfo(
                        searchInfo.copy(searchQuery = it, isActive = false)
                    )
                    banUserController.searchUser(it)
                },
                onActiveChange = { banUserController.updateSearchInfo(searchInfo.copy(isActive = it)) },
                onTrailingIconClick = {
                    if (searchInfo.searchQuery.isEmpty()) {
                        banUserController.updateSearchInfo(searchInfo.copy(isActive = false))
                    } else {
                        banUserController.updateSearchInfo(searchInfo.copy(searchQuery = ""))
                    }
                }
            )
            when (val p = process) {
                is ProcessState.Error -> ErrorComposable(p.message) {
                    banUserController.searchUser(searchInfo.searchQuery)
                }
                is ProcessState.Loading -> Loading()
                is ProcessState.Success -> {
                    LazyColumn {
                        items(count = users.size) { idx ->
                            CustomCard {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            bitmap = Utils.convertToImage(users[idx].image),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(10.dp)
                                                .clip(RoundedCornerShape(50.dp))
                                        )
                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            Text(
                                                text = "${users[idx].name} - ${users[idx].role} - Degree: ${users[idx].degree}",
                                                style = typography.h6,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                            Text(
                                                text = users[idx].email,
                                                style = typography.body1,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                            Text(
                                                text = "Address: ${users[idx].address} - Age: ${users[idx].age}",
                                                style = typography.body1,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                            Row(
                                                modifier = Modifier.align(Alignment.End),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                IconButton(onClick = { banUserController.updateCollapsible(!collapsibleEnabled[idx], idx) }) {
                                                    Icon(
                                                        imageVector = if (collapsibleEnabled[idx]) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                                                        contentDescription = null
                                                    )
                                                }
                                                BlackButton(
                                                    text = if (users[idx].isBanned) "UNBAN USER" else "BAN USER",
                                                    action = { banUserController.banUser(!users[idx].isBanned, users[idx].id, idx) { snackBarHostState.showSnackbar(it) } },
                                                    modifier = Modifier.padding(10.dp),
                                                    enabled = buttonsEnabled[idx]
                                                )
                                            }
                                        }
                                    }
                                    AnimatedVisibility(collapsibleEnabled[idx]) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp)
                                        ) {
                                            Text(
                                                text = buildAnnotatedString {
                                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                                        append("Contact Number: ")
                                                    }
                                                    append(users[idx].contactNumber)
                                                },
                                                style = typography.body1,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                            Text(
                                                text = buildAnnotatedString {
                                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                                        append("Summary: ")
                                                    }
                                                    append(users[idx].summary)
                                                },
                                                style = typography.body1,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                            Text(
                                                text = buildAnnotatedString {
                                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                                        append("Educational Background: ")
                                                    }
                                                    append(users[idx].educationalBackground)
                                                },
                                                style = typography.body1,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                            Text(
                                                text = buildAnnotatedString {
                                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                                        append("Free Tutoring Time: ")
                                                    }
                                                    append(users[idx].freeTutoringTime)
                                                },
                                                style = typography.body1,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}