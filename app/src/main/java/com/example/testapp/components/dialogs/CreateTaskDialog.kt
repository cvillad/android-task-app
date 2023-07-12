package com.example.testapp.components.dialogs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.dataStore
import com.example.testapp.viewModels.TasksViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun CreateTaskDialog(
    openDialog: MutableState<Boolean>,
    tasksViewModel: TasksViewModel = viewModel(),
) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var description by remember { mutableStateOf(TextFieldValue("")) }
    val authToken = context.dataStore.data.map { settings ->
        settings[stringPreferencesKey("authToken")]
    }.collectAsState(initial = null).value

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
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            // Pressing Ime button would move the text indicator's focus to the bottom
                            // field, if it exists!
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    scope.launch {
                        tasksViewModel.addTask(authToken!!, description.text)
                        openDialog.value = false
                    }
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