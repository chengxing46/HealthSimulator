package com.example.healthsimulator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_records")
data class MealRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,    // yyyy-MM-dd
    val mealType: String, // 早餐/午餐/晚餐/零食
    val foodName: String,
    val calories: Int,
    val timestamp: Long = System.currentTimeMillis()
)