package com.example.healthsimulator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthsimulator.data.entity.SleepRecord

@Dao
interface SleepDao {
    @Insert
    suspend fun insert(record: SleepRecord)

    @Query("SELECT * FROM sleep_records WHERE date = :date ORDER BY id DESC LIMIT 1")
    suspend fun getTodayRecord(date: String): SleepRecord?

    @Query("SELECT * FROM sleep_records WHERE date = :date ORDER BY id DESC")
    suspend fun getRecordsByDate(date: String): List<SleepRecord>

    @Query("SELECT * FROM sleep_records ORDER BY date DESC, id DESC")
    suspend fun getAllRecords(): List<SleepRecord>

    @Query("SELECT hours FROM sleep_records WHERE date = :date ORDER BY id DESC LIMIT 1")
    suspend fun getSleepForDate(date: String): Float?
}