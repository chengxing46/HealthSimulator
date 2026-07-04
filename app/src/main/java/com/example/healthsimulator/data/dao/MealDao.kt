package com.example.healthsimulator.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.healthsimulator.data.entity.MealRecord

@Dao
interface MealDao {
    @Insert
    suspend fun insert(record: MealRecord)

    @Delete
    suspend fun delete(record: MealRecord)

    @Query("SELECT SUM(calories) FROM meal_records WHERE date = :date")
    suspend fun getTotalCalories(date: String): Int?

    @Query("SELECT * FROM meal_records WHERE date = :date ORDER BY id DESC")
    suspend fun getRecordsByDate(date: String): List<MealRecord>

    @Query("SELECT * FROM meal_records ORDER BY date DESC, id DESC")
    suspend fun getAllRecords(): List<MealRecord>

    @Query("SELECT SUM(calories) FROM meal_records WHERE date = :date")
    suspend fun getCaloriesForDate(date: String): Int?
}