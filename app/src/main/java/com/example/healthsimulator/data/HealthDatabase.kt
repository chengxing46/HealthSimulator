package com.example.healthsimulator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthsimulator.data.dao.MealDao
import com.example.healthsimulator.data.dao.SleepDao
import com.example.healthsimulator.data.dao.StepDao
import com.example.healthsimulator.data.dao.WaterDao
import com.example.healthsimulator.data.entity.MealRecord
import com.example.healthsimulator.data.entity.SleepRecord
import com.example.healthsimulator.data.entity.StepRecord
import com.example.healthsimulator.data.entity.WaterRecord

@Database(
    entities = [StepRecord::class, WaterRecord::class, SleepRecord::class, MealRecord::class],
    version = 1,
    exportSchema = false
)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun stepDao(): StepDao
    abstract fun waterDao(): WaterDao
    abstract fun sleepDao(): SleepDao
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: HealthDatabase? = null

        fun getDatabase(context: Context): HealthDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthDatabase::class.java,
                    "health_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}