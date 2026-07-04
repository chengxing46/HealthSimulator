package com.example.healthsimulator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_records")
data class WaterRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,    // yyyy-MM-dd
    val cups: Int,       // 杯数 (1杯 = 250ml)
    val timestamp: Long = System.currentTimeMillis()
)