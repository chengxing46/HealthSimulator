package com.example.healthsimulator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_records")
data class SleepRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,    // yyyy-MM-dd
    val hours: Float,    // 睡眠小时数
    val timestamp: Long = System.currentTimeMillis()
)