package com.example.myfirstapp.todo.ui.items

import android.app.Application
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.res.stringResource
import com.example.myfirstapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.core.Result
import com.example.myfirstapp.todo.data.Cpu
import com.example.myfirstapp.todo.utils.MyNetworkStatusViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(onItemClick: (id: String?) -> Unit, onAddItem: () -> Unit, onLogout: () -> Unit){
    Log.d("ItemsScreen", "Recompose")
    val itemsViewModel = viewModel<ItemsViewModel>(factory = ItemsViewModel.Factory)
    val itemsUiState by itemsViewModel.uiState.collectAsStateWithLifecycle(
        initialValue = listOf()
    )

    var isEditing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Log.d("ItemsScreen", isEditing.toString())
//    val myNewtworkStatusViewModel = viewModel<MyNetworkStatusViewModel>(
//        factory = MyNetworkStatusViewModel.Factory(
//            LocalContext.current.applicationContext as Application
//        )
//    )
//
//    LaunchedEffect(myNewtworkStatusViewModel.uiState){
//        if(myNewtworkStatusViewModel.uiState){
//            myNewtworkStatusViewModel.startJob()
//        }
//    }

    suspend fun expandAddButton() {
        if (!isEditing) {
            isEditing = true
            delay(1500L)
            isEditing = false
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.items)) },
                actions = {
                    Button(onClick = onLogout) { Text("Logout") }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        expandAddButton()
                        Log.d("ItemsScreen", "add")
                        onAddItem()
                    }
//                    Log.d("ItemsScreen", "add")
//                    onAddItem()
                },
            ) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(Icons.Rounded.Add, null)
                    AnimatedVisibility(visible = isEditing) {
                        Text(
                            text = "Add",
                            modifier = Modifier
                                .padding(start = 8.dp, top = 3.dp)
                        )
                    }}
            }
//                Icon(Icons.Rounded.Add, null)
//                AnimatedVisibility(visible = !isEditing) {
//                    Text(
//                        text = "Add",
//                        modifier = Modifier
//                            .padding(start = 8.dp, top = 3.dp)
//                    )
//                }}
        }
    ){
        ItemList(cpuList = itemsUiState, onItemClick = onItemClick, modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
fun PreviewItemsScreen() {
    ItemsScreen(onItemClick = {}, onAddItem = {}, onLogout = {})
}