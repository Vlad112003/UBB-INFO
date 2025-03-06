package com.example.myfirstapp.todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myfirstapp.todo.data.Cpu
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM cpus")
    fun getAllCpus(): Flow<List<Cpu>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCpu(item: Cpu)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCpus(items: List<Cpu>)

    @Update
    suspend fun updateCpu(item: Cpu): Int

    @Query("DELETE FROM cpus WHERE _id = :id")
    suspend fun deleteCpuById(id: String): Int

    @Query("DELETE FROM cpus")
    suspend fun deleteAllCpus(): Int

    @Query("DELETE FROM cpus WHERE model = :model")
    suspend fun deleteCpuByName(model: String): Int

    @Query("SELECT COUNT(*) FROM cpus WHERE isSaved = 0")
    suspend fun getUnsavedCpuCount(): Int

    @Query("SELECT * FROM cpus WHERE isSaved = 0")
    suspend fun getLocallySavedCpus(): List<Cpu>

    @Delete
    suspend fun deleteCpu(cpu: Cpu)

    @Query("SELECT * FROM cpus WHERE _id = :cpuId LIMIT 1")
    suspend fun getCpuById(cpuId: String): Cpu?
}