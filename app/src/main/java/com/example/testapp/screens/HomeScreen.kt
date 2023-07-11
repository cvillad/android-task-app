package com.example.testapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.example.testapp.components.dialogs.CreateTaskDialog
import com.example.testapp.dataStore
import com.example.testapp.models.Task
import com.example.testapp.viewModels.TasksViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val tasksViewModel by remember { mutableStateOf(TasksViewModel()) }
    val tasks by remember { mutableStateOf(tasksViewModel.getTasks()) }
    val openDialog = remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        tasksViewModel.addTask(Task(description = "First task"))
        tasksViewModel.addTask(Task(description = "Final task"))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Todo app")
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu icon")
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                scope.launch {
                                    context.dataStore.edit { settings ->
                                        settings.remove(stringPreferencesKey("authToken"))
                                    }
                                }
                            }
                        ) {
                            Text(text = "Cerrar sesión")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (openDialog.value) {
                CreateTaskDialog(openDialog = openDialog, tasksViewModel = tasksViewModel)
            }
            FloatingActionButton(onClick = { openDialog.value = true }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "TODO item Add",
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 30.dp)
            ) {
                tasks.forEach {task->
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.completed,
                            onCheckedChange = {
                                tasksViewModel.markAsComplete(task, it)
                            }
                        )
                        Text(text = task.description)
                    }
                }
            }
        }
    }
}