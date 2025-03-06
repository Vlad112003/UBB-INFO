package com.example.myfirstapp.todo.data.remote

import com.example.myfirstapp.todo.data.Cpu
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ItemService {
    @GET("/api/cpu")
    suspend fun findCpus(@Header("Authorization") authorization: String): List<Cpu>

    @GET("/api/cpu/{id}")
    suspend fun readCpu(@Header("Authorization") authorization: String, @Path("id") itemId: String?): Cpu

    @Headers("Content-Type: application/json")
    @POST("/api/cpu")
    suspend fun createCpu(@Header("Authorization") authorization: String, @Body cpu: Cpu): Cpu

    @Headers("Content-Type: application/json")
    @PUT("/api/cpu/{id}")
    suspend fun updateCpu(@Header("Authorization") authorization: String, @Path("id") itemId: String?, @Body cpu: Cpu): Cpu
}