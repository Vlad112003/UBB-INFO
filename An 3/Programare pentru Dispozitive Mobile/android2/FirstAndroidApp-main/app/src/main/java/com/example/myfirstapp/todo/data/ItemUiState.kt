package com.example.myfirstapp.todo.data

import com.example.myfirstapp.core.Result

data class ItemUiState(
    val cpu: Cpu = Cpu(),
    val loadResult: Result<Cpu> = Result.Loading,
    val submitResult: Result<Cpu>? = null
)