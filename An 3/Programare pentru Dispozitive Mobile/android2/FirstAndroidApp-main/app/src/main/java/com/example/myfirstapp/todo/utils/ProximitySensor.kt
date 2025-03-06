package com.example.myfirstapp.todo.utils

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

class ProximitySensorViewModel(application: Application) : AndroidViewModel(application) {
    var uiState by mutableDoubleStateOf(0.0)
        private set

    init {
        viewModelScope.launch {
            ProximitySensorMonitor(getApplication()).isNear.collect {
                uiState = it.toDouble()
            }
        }
    }

    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ProximitySensorViewModel(application)
            }
        }
    }
}

@Composable
fun ProximitySensor() {
    val proximitySensorViewModel = viewModel<ProximitySensorViewModel>(
        factory = ProximitySensorViewModel.Factory(
            LocalContext.current.applicationContext as Application
        )
    )
    Column() {
        Text(text = "ProximitySensor - Device is at distance: ${proximitySensorViewModel.uiState} cm",
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.Black
            )
        )
    }
}