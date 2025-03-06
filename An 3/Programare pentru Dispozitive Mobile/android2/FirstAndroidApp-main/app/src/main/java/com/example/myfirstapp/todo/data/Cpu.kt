package com.example.myfirstapp.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cpus")
data class Cpu(
    @PrimaryKey var _id: String = "",
    var model: String = "",
    var manufacturer: String = "",
    var speed: Double = 0.0,
    var cores: Int = 0,
    var threads: Int = 0,
    var userId: String = "",
    var isSaved: Boolean = true,
    var lat: Double = 0.0,
    var lon: Double = 0.0
)