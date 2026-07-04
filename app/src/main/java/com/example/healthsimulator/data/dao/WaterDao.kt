package com.example.healthsimulator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthsimulator.data.entity.WaterRecord

@Dao
interface WaterDao {
    @Insert
    suspend fun insert(record: WaterRecord)

    @Query("SELECT SUM(cups) FROM water_records WHERE date = :date")
    suspend fun getTotalCups(date: String): Int?

    @Query("SELECT * FROM water_records WHERE date = :date ORDER BY id DESC")
    suspend fun getRecordsByDate(date: String): List<WaterRecord>

    @Query("SELECT * FROM water_records ORDER BY date DESC, id DESC")
    suspend fun getAllRecords(): List<WaterRecord>

    @Query("SELECT SUM(cups) FROM water_records WHERE date = :date")
    suspend fun getCupsForDate(date: String): Int?
}