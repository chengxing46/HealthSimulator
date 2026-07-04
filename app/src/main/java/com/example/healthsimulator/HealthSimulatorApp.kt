package com.example.healthsimulator

import android.app.Application
import com.example.healthsimulator.data.HealthDatabase

class HealthSimulatorApp : Application() {
    val database: HealthDatabase by lazy { HealthDatabase.getDatabase(this) }
}