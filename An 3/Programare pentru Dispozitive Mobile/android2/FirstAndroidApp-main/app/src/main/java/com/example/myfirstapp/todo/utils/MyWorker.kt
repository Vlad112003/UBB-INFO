package com.example.myfirstapp.todo.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myfirstapp.MyFirstApplication
import com.example.myfirstapp.todo.data.ItemRepository
import com.example.myfirstapp.todo.data.Cpu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit.SECONDS

class MyWorker(
    context: Context,
    val workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val itemRepository = (applicationContext as MyFirstApplication).container.itemRepository

        val notSaved = itemRepository.getLocallySavedCpus()
        Log.d("MyWorker", notSaved.toString())

        notSaved.forEach { cpu ->
            if (cpu._id.length < 12) {
                cpu._id = ""
                val res = itemRepository.save(cpu)  // am pus isSaved = true in repository
            } else {
                val res = itemRepository.update(cpu)
            }
        }
        return Result.success()
    }
}