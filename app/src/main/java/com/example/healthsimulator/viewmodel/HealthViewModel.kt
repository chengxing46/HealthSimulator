package com.example.healthsimulator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthsimulator.HealthSimulatorApp
import com.example.healthsimulator.data.entity.MealRecord
import com.example.healthsimulator.data.entity.SleepRecord
import com.example.healthsimulator.data.entity.StepRecord
import com.example.healthsimulator.data.entity.WaterRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HealthViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as HealthSimulatorApp).database
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // 今日数据State
    data class TodayHealthData(
        val steps: Int = 0,
        val waterCups: Int = 0,
        val sleepHours: Float = 0f,
        val calories: Int = 0
    )

    private val _todayData = MutableStateFlow(TodayHealthData())
    val todayData: StateFlow<TodayHealthData> = _todayData.asStateFlow()

    // 周数据
    data class WeeklySteps(val date: String, val steps: Int)

    private val _weeklySteps = MutableStateFlow<List<WeeklySteps>>(emptyList())
    val weeklySteps: StateFlow<List<WeeklySteps>> = _weeklySteps.asStateFlow()

    // 今日饮食记录
    private val _todayMeals = MutableStateFlow<List<MealRecord>>(emptyList())
    val todayMeals: StateFlow<List<MealRecord>> = _todayMeals.asStateFlow()

    // 历史记录
    private val _allStepRecords = MutableStateFlow<List<StepRecord>>(emptyList())
    val allStepRecords: StateFlow<List<StepRecord>> = _allStepRecords.asStateFlow()

    private val _allWaterRecords = MutableStateFlow<List<WaterRecord>>(emptyList())
    val allWaterRecords: StateFlow<List<WaterRecord>> = _allWaterRecords.asStateFlow()

    private val _allSleepRecords = MutableStateFlow<List<SleepRecord>>(emptyList())
    val allSleepRecords: StateFlow<List<SleepRecord>> = _allSleepRecords.asStateFlow()

    private val _allMealRecords = MutableStateFlow<List<MealRecord>>(emptyList())
    val allMealRecords: StateFlow<List<MealRecord>> = _allMealRecords.asStateFlow()

    init {
        refreshAllData()
    }

    fun refreshAllData() {
        viewModelScope.launch {
            val today = dateFormat.format(Date())
            refreshTodayData(today)
            refreshWeeklyData()
            loadAllHistory()
        }
    }

    private suspend fun refreshTodayData(today: String) {
        val steps = db.stepDao().getTotalSteps(today) ?: 0
        val water = db.waterDao().getTotalCups(today) ?: 0
        val sleep = db.sleepDao().getSleepForDate(today) ?: 0f
        val calories = db.mealDao().getTotalCalories(today) ?: 0

        _todayData.value = TodayHealthData(steps, water, sleep, calories)
        _todayMeals.value = db.mealDao().getRecordsByDate(today)
    }

    private suspend fun refreshWeeklyData() {
        val dates = mutableListOf<String>()
        val cal = java.util.Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        for (i in 6 downTo 0) {
            val d = java.util.Calendar.getInstance()
            d.timeInMillis = cal.timeInMillis - i * 86400000L
            dates.add(sdf.format(d.time))
        }

        val weeklyList = dates.map { date ->
            val steps = db.stepDao().getStepsForDate(date) ?: 0
            WeeklySteps(date, steps)
        }
        _weeklySteps.value = weeklyList
    }

    private suspend fun loadAllHistory() {
        _allStepRecords.value = db.stepDao().getAllRecords()
        _allWaterRecords.value = db.waterDao().getAllRecords()
        _allSleepRecords.value = db.sleepDao().getAllRecords()
        _allMealRecords.value = db.mealDao().getAllRecords()
    }

    // ===== 操作函数 =====

    fun addSteps(steps: Int) {
        viewModelScope.launch {
            val today = dateFormat.format(Date())
            db.stepDao().insert(StepRecord(date = today, steps = steps))
            refreshAllData()
        }
    }

    fun addWater() {
        viewModelScope.launch {
            val today = dateFormat.format(Date())
            db.waterDao().insert(WaterRecord(date = today, cups = 1))
            refreshAllData()
        }
    }

    fun setSleep(hours: Float) {
        viewModelScope.launch {
            val today = dateFormat.format(Date())
            db.sleepDao().insert(SleepRecord(date = today, hours = hours))
            refreshAllData()
        }
    }

    fun addMeal(mealType: String, foodName: String, calories: Int) {
        viewModelScope.launch {
            val today = dateFormat.format(Date())
            db.mealDao().insert(MealRecord(date = today, mealType = mealType, foodName = foodName, calories = calories))
            refreshAllData()
        }
    }

    fun deleteMeal(record: MealRecord) {
        viewModelScope.launch {
            db.mealDao().delete(record)
            refreshAllData()
        }
    }
}