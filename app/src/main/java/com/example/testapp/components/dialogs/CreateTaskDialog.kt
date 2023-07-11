package com.example.testapp.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.models.Task
import com.example.testapp.viewModels.TasksViewModel

@Composable
fun CreateTaskDialog(
    openDialog: MutableState<Boolean>,
    tasksViewModel: TasksViewModel = viewModel(),
) {
    var description by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        shape = RoundedCornerShape(4.dp),
        title = {
            Text(text = "Crear tarea")
        },
        text = {
            Column(
                modifier = Modifier.padding(10.dp),
            ) {
                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    openDialog.value = false
                    tasksViewModel.addTask(Task(description = description.text))
                }
            ) {
                Text(text = "Crear")
            }
        },
        dismissButton = {
            Button(onClick = { openDialog.value = false }) {
                Text(text = "Cancelar")
            }
        }
    )
}