package com.example.healthsimulator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step_records")
data class StepRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,    // yyyy-MM-dd
    val steps: Int,
    val timestamp: Long = System.currentTimeMillis()
)