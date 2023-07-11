package com.example.testapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testapp.models.Task
import com.example.testapp.viewModels.TasksViewModel

@SuppressLint("UnrememberedMutableState", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val tasksViewModel by remember { mutableStateOf(TasksViewModel()) }
    val tasks by remember { mutableStateOf(tasksViewModel.getTasks()) }

    LaunchedEffect(null) {
        tasksViewModel.addTask(Task(description = "First task"))
        tasksViewModel.addTask(Task(description = "Final task"))
    }

    Scaffold(
        topBar = {
            TopAppBar() {
                Text(text = "Todo List")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
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