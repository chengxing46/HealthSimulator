package com.example.healthsimulator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthsimulator.data.entity.StepRecord

@Dao
interface StepDao {
    @Insert
    suspend fun insert(record: StepRecord)

    @Query("SELECT * FROM step_records WHERE date = :date ORDER BY id DESC LIMIT 1")
    suspend fun getTodayRecord(date: String): StepRecord?

    @Query("SELECT SUM(steps) FROM step_records WHERE date = :date")
    suspend fun getTotalSteps(date: String): Int?

    @Query("SELECT * FROM step_records WHERE date = :date ORDER BY id DESC")
    suspend fun getRecordsByDate(date: String): List<StepRecord>

    @Query("SELECT * FROM step_records ORDER BY date DESC, id DESC")
    suspend fun getAllRecords(): List<StepRecord>

    @Query("SELECT DISTINCT date FROM step_records ORDER BY date DESC LIMIT 7")
    suspend fun getLast7Days(): List<String>

    @Query("SELECT SUM(steps) FROM step_records WHERE date = :date")
    suspend fun getStepsForDate(date: String): Int?
}