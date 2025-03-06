package com.example.myfirstapp.todo.ui.item

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirstapp.R
import com.example.myfirstapp.core.Result
import com.example.myfirstapp.todo.data.ItemViewModel
import com.example.myfirstapp.todo.utils.MyMap
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(itemId: String?, onClose: () -> Unit) {
    val itemViewModel = viewModel<ItemViewModel>(factory = ItemViewModel.Factory(itemId))
    val itemUiState = itemViewModel.uiState

    var model by rememberSaveable { mutableStateOf(itemUiState.cpu.model) }
    var manufacturer by rememberSaveable { mutableStateOf(itemUiState.cpu.manufacturer) }
    var speed by rememberSaveable { mutableStateOf(itemUiState.cpu.speed.toString()) }
    var cores by rememberSaveable { mutableStateOf(itemUiState.cpu.cores.toString()) }
    var threads by rememberSaveable { mutableStateOf(itemUiState.cpu.threads.toString()) }
    var userId by rememberSaveable { mutableStateOf(itemUiState.cpu.userId) }
    var lat by rememberSaveable {
        mutableDoubleStateOf(itemUiState.cpu.lat)
    }

    var lon by rememberSaveable {
        mutableDoubleStateOf(itemUiState.cpu.lon)
    }

    Log.d("ItemScreen", "recompose, text = $model, $manufacturer, $speed, $cores, $threads, $userId, $lat, $lon")

    var loading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    suspend fun loadMap() {
        if (!loading) {
            loading = true
            delay(1500L)
            loading = false
        }
    }

    val onLocationChanged: (Double, Double) -> Unit = { newLat: Double, newLon: Double ->
        // Update the lat and lon states
        Log.d("ItemScreen", "onLocationChanged: $newLat, $newLon")
        lat = newLat
        lon = newLon
    }

    LaunchedEffect(itemUiState.submitResult) {
        Log.d("ItemScreen", "Submit = ${itemUiState.submitResult}")
        if (itemUiState.submitResult is Result.Success) {
            Log.d("ItemScreen", "Closing screen")
            onClose()
        }
    }

    var textInitialized by remember { mutableStateOf(itemId == null) }
    LaunchedEffect(itemId, itemUiState.loadResult) {
        Log.d("ItemScreen", "Text initialized = ${itemUiState.loadResult}")
        if (textInitialized) {
            return@LaunchedEffect
        }
        if (!(itemUiState.loadResult is Result.Loading)) {
            model = itemUiState.cpu.model
            manufacturer = itemUiState.cpu.manufacturer
            speed = itemUiState.cpu.speed.toString()
            cores = itemUiState.cpu.cores.toString()
            threads = itemUiState.cpu.threads.toString()
            userId = itemUiState.cpu.userId
            textInitialized = true
            coroutineScope.launch {
                loadMap()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item)) },
                actions = {
                    Button(onClick = {
                        Log.d("ItemScreen", "save item text = $model")
                        itemViewModel.updateItem(model, manufacturer, speed.toDouble(), cores.toInt(), threads.toInt(), userId)
                    }) { Text("Update") }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (itemUiState.loadResult is Result.Loading) {
                CircularProgressIndicator()
                return@Scaffold
            }
            if (itemUiState.submitResult is Result.Loading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { LinearProgressIndicator() }
            }
            if (itemUiState.loadResult is Result.Error) {
                Text(text = "Failed to load item - ${(itemUiState.loadResult as Result.Error).exception?.message}")
            }
            Column {
                TextField(
                    value = model,
                    onValueChange = { model = it }, label = { Text("Model") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = manufacturer,
                    onValueChange = { manufacturer = it }, label = { Text("Manufacturer") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = speed,
                    onValueChange = { speed = it }, label = { Text("Speed") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = cores,
                    onValueChange = { cores = it }, label = { Text("Cores") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = threads,
                    onValueChange = { threads = it }, label = { Text("Threads") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = userId,
                    onValueChange = { userId = it }, label = { Text("User ID") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row {
                    if (loading) {
                        LoadingRow()
                    } else {
                        MyMap(cpuId = itemId ?: "", lat = lat, lon = lon, onLocationChanged = onLocationChanged)
                    }
                }
            }
            if (itemUiState.submitResult is Result.Error) {
                Text(
                    text = "Failed to submit item - ${(itemUiState.submitResult as Result.Error).exception?.message}",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun LoadingRow() {
    val infiniteTransition = rememberInfiniteTransition(label = "loadingRowLabel")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        ), label = "mapAlpha"
    )
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Gray.copy(alpha = alpha))
        )
    }
}

@Preview
@Composable
fun PreviewItemScreen() {
    ItemScreen(itemId = "0", onClose = {})
}