package com.example.myfirstapp.todo.data.remote

import com.example.myfirstapp.todo.data.Cpu

data class Payload(val updatedCpu: Cpu)

data class ItemEvent(val event: String, val payload: Payload)