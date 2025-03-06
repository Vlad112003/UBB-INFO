package com.example.myfirstapp.todo.ui.items

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myfirstapp.MyFirstApplication
import com.example.myfirstapp.core.TAG
import com.example.myfirstapp.todo.data.Cpu
import com.example.myfirstapp.todo.data.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ItemsViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    val uiState: Flow<List<Cpu>> = itemRepository.cpuStream

    init {
        Log.d(TAG, "init")
        loadItems()
    }

    fun loadItems() {
        Log.d(TAG, "loadItems...")
        viewModelScope.launch {
            itemRepository.refresh()
        }
    }

    fun deleteCpu(cpu: Cpu) {
        viewModelScope.launch {
            itemRepository.deleteCpu(cpu)
        }
    }

    fun updateCpuLocation(cpu: Cpu, lat: Double, lon: Double) {
        viewModelScope.launch {
            val updatedCpu = cpu.copy(lat = lat, lon = lon)
            itemRepository.update(updatedCpu)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyFirstApplication)
                ItemsViewModel(app.container.itemRepository)
            }
        }
    }
}