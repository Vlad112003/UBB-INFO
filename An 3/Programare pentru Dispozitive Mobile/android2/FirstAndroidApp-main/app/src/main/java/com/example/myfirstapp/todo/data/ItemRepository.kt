package com.example.myfirstapp.todo.data

import android.util.Log
import com.example.myfirstapp.core.Result
import com.example.myfirstapp.core.TAG
import com.example.myfirstapp.core.data.remote.Api
import com.example.myfirstapp.todo.data.local.ItemDao
import com.example.myfirstapp.todo.data.remote.ItemEvent
import com.example.myfirstapp.todo.data.remote.ItemService
import com.example.myfirstapp.todo.data.remote.ItemWsClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

class ItemRepository(
    private val itemService: ItemService,
    private val itemWsClient: ItemWsClient,
    private val itemDao: ItemDao
) {

    private val localCpuList = mutableListOf<Cpu>()

    val cpuStream by lazy { itemDao.getAllCpus() }

    init {
        Log.d(TAG, "init")
    }

    suspend fun refresh() {
        Log.d(TAG, "refresh started")
        try {
            val cpus = itemService.findCpus(authorization = getBearerToken())
            itemDao.deleteAllCpus()
            cpus.forEach { itemDao.insertCpu(it) }
            Log.d(TAG, "refresh succeeded")
        } catch (e: Exception) {
            Log.w(TAG, "refresh failed", e)
        }
    }

    suspend fun openWsClient() {
        Log.d(TAG, "openWsClient")
        withContext(Dispatchers.IO) {
            getItemEvents().collect {
                Log.d(TAG, "Item event collected $it")
                if (it is Result.Success) {
                    val itemEvent = it.data
                    when (itemEvent.event) {
                        "created" -> handleItemCreated(itemEvent.payload.updatedCpu)
                        "updated" -> handleItemUpdated(itemEvent.payload.updatedCpu)
                        "deleted" -> handleItemDeleted(itemEvent.payload.updatedCpu)
                    }
                }
            }
        }
    }

    suspend fun closeWsClient() {
        Log.d(TAG, "closeWsClient")
        withContext(Dispatchers.IO) {
            itemWsClient.closeSocket()
        }
    }

    suspend fun getItemEvents(): Flow<Result<ItemEvent>> = callbackFlow {
        Log.d(TAG, "getItemEvents started")
        itemWsClient.openSocket(
            onEvent = {
                Log.d(TAG, "onEvent $it")
                if (it != null) {
                    Log.d(TAG, "onEvent trySend $it")
                    trySend(Result.Success(it))
                }
            },
            onClosed = { close() },
            onFailure = { close() })
        awaitClose { itemWsClient.closeSocket() }
    }

    suspend fun update(cpu: Cpu): Cpu {
        Log.d(TAG, "update $cpu...")
        val updatedCpu = itemService.updateCpu(authorization = getBearerToken(), cpu._id, cpu)
        Log.d(TAG, "update $cpu succeeded")
        handleItemUpdated(updatedCpu)
        return updatedCpu
    }

    suspend fun save(cpu: Cpu): Cpu {
        Log.d(TAG, "save $cpu...")
        val createdCpu = itemService.createCpu(authorization = getBearerToken(), cpu)
        Log.d(TAG, "save $cpu succeeded")
        handleItemCreated(createdCpu)
        return createdCpu
    }

    suspend fun deleteCpu(cpu: Cpu) {
        itemDao.deleteCpu(cpu)
    }

    private suspend fun handleItemDeleted(cpu: Cpu) {
        Log.d(TAG, "handleItemDeleted - todo $cpu")
    }

    private suspend fun handleItemUpdated(cpu: Cpu) {
        Log.d(TAG, "handleItemUpdated...: $cpu")
        itemDao.updateCpu(cpu)
    }

    private suspend fun handleItemCreated(cpu: Cpu) {
        Log.d(TAG, "handleItemCreated...: $cpu")
        itemDao.insertCpu(cpu)
    }

    suspend fun deleteAll() {
        itemDao.deleteAllCpus()
    }

    fun setToken(token: String) {
        itemWsClient.authorize(token)
    }

    suspend fun getLocallySavedCpus(): List<Cpu> {
        return itemDao.getLocallySavedCpus()
    }

    suspend fun getNrUnsaved(): Int {
        return itemDao.getUnsavedCpuCount()
    }

    suspend fun addLocally(cpu: Cpu) {
        cpu.isSaved = false
        itemDao.insertCpu(cpu)
    }

    suspend fun updateLocally(cpu: Cpu) {
        cpu.isSaved = false
        itemDao.updateCpu(cpu)
    }

    suspend fun syncWithDatabase() {
        val unsavedCpus = itemDao.getLocallySavedCpus()
        unsavedCpus.forEach { cpu ->
            try {
                save(cpu)
                cpu.isSaved = true
                itemDao.updateCpu(cpu)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to sync CPU: ${cpu._id}", e)
            }
        }
    }

    suspend fun updateLatLon(cpuId: String, lat: Double, lon: Double) {
        val cpu = itemDao.getCpuById(cpuId)
        if (cpu != null) {
            cpu.lat = lat
            cpu.lon = lon
            itemDao.updateCpu(cpu)
        }
    }

    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"
}