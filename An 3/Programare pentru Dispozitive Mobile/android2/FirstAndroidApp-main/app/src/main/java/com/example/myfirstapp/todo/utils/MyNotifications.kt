package com.example.myfirstapp.todo.utils

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirstapp.core.Result
import com.example.myfirstapp.todo.data.ItemViewModel
import com.example.myfirstapp.todo.utils.createNotificationChannel
import com.example.myfirstapp.todo.utils.showSimpleNotification

@Composable
fun MyNotifications() {
    val itemViewModel = viewModel<ItemViewModel>(factory = ItemViewModel.Factory(null))
    val itemUiState = itemViewModel.uiState

    val context = LocalContext.current
    val channelId = "MyTestChannel"
    val notificationId = 0

    Log.d("My notifications", "Recompose")
    LaunchedEffect(Unit) {
        createNotificationChannel(channelId, context)
    }

    LaunchedEffect(itemUiState.submitResult) {
        Log.d("My notifications", "Submit = ${itemUiState.submitResult}");
        if (itemUiState.submitResult is Result.Success) {
            showSimpleNotification(
                context,
                channelId,
                notificationId,
                "New simple notification",
                "Saved song!"
            )
        }
        if(itemUiState.submitResult is Result.Error){
            showSimpleNotification(
                context,
                channelId,
                notificationId,
                "New simple notification",
                "Saved song locally!"
            )
        }
    }
}
