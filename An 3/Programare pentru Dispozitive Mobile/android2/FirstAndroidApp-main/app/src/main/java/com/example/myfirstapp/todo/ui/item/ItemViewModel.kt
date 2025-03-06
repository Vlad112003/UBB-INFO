package com.example.myfirstapp.todo.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myfirstapp.MyFirstApplication
import com.example.myfirstapp.core.Result
import com.example.myfirstapp.core.TAG
import kotlinx.coroutines.launch

class ItemViewModel(
    private val itemId: String?,
    private val itemRepository: ItemRepository
) : ViewModel() {

    var uiState by mutableStateOf(ItemUiState())
        private set

    init {
        Log.d(TAG, "init with id: ${itemId}")
        if (itemId != null) {
            loadItem()
        } else {
            uiState = uiState.copy(loadResult = Result.Success(Cpu()))
        }
    }

    fun loadItem() {
        viewModelScope.launch {
            itemRepository.cpuStream.collect { items ->
                if (!(uiState.loadResult is Result.Loading)) {
                    return@collect
                }
                Log.d(TAG, "searching for ${itemId}")
                val cpu = items.find { it._id == itemId } ?: Cpu()
                Log.d(TAG, "found ${cpu}")
                uiState = uiState.copy(cpu = cpu, loadResult = Result.Success(cpu))
            }
        }
    }

    fun saveItem(model: String, manufacturer: String, speed: Double, cores: Int, threads: Int, userId: String) {
        viewModelScope.launch {
            Log.d(TAG, "save new cpu!!!")
            try {
                uiState = uiState.copy(submitResult = Result.Loading)
                val item = uiState.cpu.copy(model = model, manufacturer = manufacturer, speed = speed, cores = cores, threads = threads, userId = userId, _id = "")
                val savedCpu: Cpu
                savedCpu = itemRepository.save(item)
                Log.d(TAG, "save cpu succeeded!!!")
                uiState = uiState.copy(submitResult = Result.Success(savedCpu))
            } catch (e: Exception) {
                Log.d(TAG, "saveOrUpdateItem failed")
                val nrUnsaved = itemRepository.getNrUnsaved()
                val currentId = nrUnsaved + 1
                uiState = uiState.copy(submitResult = Result.Error(e))
                val item = uiState.cpu.copy(model = model, manufacturer = manufacturer, speed = speed, cores = cores, threads = threads, userId = userId, isSaved = false, _id = currentId.toString())
                itemRepository.addLocally(item)
                Log.d(TAG, "added item ${item} locally")
            }
        }
    }

    fun updateItem(model: String, manufacturer: String, speed: Double, cores: Int, threads: Int, userId: String) {
        viewModelScope.launch {
            Log.d(TAG, "update Cpu!!!")
            try {
                uiState = uiState.copy(submitResult = Result.Loading)
                val item = uiState.cpu.copy(model = model, manufacturer = manufacturer, speed = speed, cores = cores, threads = threads, userId = userId, isSaved = true)
                val savedCpu: Cpu
                savedCpu = itemRepository.update(item)
                Log.d(TAG, "updateItem succeeded")
                uiState = uiState.copy(submitResult = Result.Success(savedCpu))
            } catch (e: Exception) {
                Log.d(TAG, "saveOrUpdateItem failed")
                uiState = uiState.copy(submitResult = Result.Error(e))
                val item = uiState.cpu.copy(model = model, manufacturer = manufacturer, speed = speed, cores = cores, threads = threads, userId = userId, isSaved = false)
                itemRepository.updateLocally(item)
            }
        }
    }

    fun updateLatLon(cpuId: String, lat: Double, lon: Double) {
        viewModelScope.launch {
            itemRepository.updateLatLon(cpuId, lat, lon)
        }
    }

    fun syncItems() {
        viewModelScope.launch {
            itemRepository.syncWithDatabase()
        }
    }

    companion object {
        fun Factory(itemId: String?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyFirstApplication)
                ItemViewModel(itemId, app.container.itemRepository)
            }
        }
    }
}