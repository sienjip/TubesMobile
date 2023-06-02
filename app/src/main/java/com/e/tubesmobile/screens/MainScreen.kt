package com.e.tubesmobile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.e.tubesmobile.ui.theme.Purple700
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val title = remember { mutableStateOf("") }
    val appBarHorizontalPadding = 4.dp
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Purple700,
                elevation = 0.dp,
                modifier= Modifier.fillMaxWidth()) {
                Box(Modifier.height(32.dp)) {
                    Row(Modifier.fillMaxHeight()
                        .width(72.dp - appBarHorizontalPadding),
                        verticalAlignment = Alignment.CenterVertically) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides
                                    ContentAlpha.high,
                        ) {
                            IconButton(
                                onClick = { },
                                enabled = true,
                            ) {
                                Icon(
                                    Icons.Filled.Menu, null,
                                    tint = Color.White)
                            }
                        }
                    }

                    Row(Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically) {
                        ProvideTextStyle(value = MaterialTheme.typography.h6) {
                            CompositionLocalProvider(
                                LocalContentAlpha provides ContentAlpha.high,
                            ){
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    maxLines = 1,
                                    text = title.value
                                )
                            }
                        }
                    }
                }
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    actionColor = Color.Green,
                    contentColor = Color.White,
                    snackbarData = data
                )
            }
        },
        drawerContent = {
            DrawerContent { route ->
                navController.navigate(route)
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        },
        bottomBar = {
            BottomNavigationComposable(title.value, onClick =
            { tab ->
                navController.navigate(tab.route)
            })
        },
    )
    { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavHost(navController = navController,
                startDestination = "pengelolaan-komputer") {
                composable("home") {
                    HomeScreen()
                }
                composable("setting") {
                    SettingScreen()
                }
                composable("pengelolaan-komputer") {
                    title.value = "Pengelolaan Komputer"
                    PengelolaanKomputerScreen(navController = navController, snackbarHostState = scaffoldState.snackbarHostState , modifier = Modifier.padding((innerPadding)))
                }
                composable("tambah-pengelolaan-komputer") {
                    title.value = "Tambah Pengelolaan Komputer"
                    FormPencatatanKomputer(navController = navController, modifier = Modifier.padding(innerPadding))
                }
                composable("edit-pengelolaan-komputer/{id}",
                    listOf(
                        navArgument("id") {
                            type = NavType.StringType
                        }
                    )) { backStackEntry ->
                    title.value = "Edit Pengelolaan Komputer"
                    val id = backStackEntry.arguments?.getString("id") ?: return@composable
                    FormPencatatanKomputer(navController = navController, id = id, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}