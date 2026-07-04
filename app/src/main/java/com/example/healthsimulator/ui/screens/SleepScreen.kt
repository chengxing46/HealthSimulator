package com.example.healthsimulator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.healthsimulator.ui.theme.SleepColor
import com.example.healthsimulator.viewmodel.HealthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepScreen(viewModel: HealthViewModel) {
    val todayData by viewModel.todayData.collectAsState()
    val allRecords by viewModel.allSleepRecords.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var inputHours by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 今日概览
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SleepColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "今日睡眠",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = String.format("%.1f", todayData.sleepHours),
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = " / 8.0 小时",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (todayData.sleepHours / 8f).coerceAtMost(1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 添加睡眠按钮
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SleepColor)
        ) {
            Icon(Icons.Default.Bedtime, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("记录睡眠")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "睡眠记录",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (allRecords.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("暂无记录", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allRecords) { record ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = record.date,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                            Text(
                                text = "${record.hours} 小时",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SleepColor
                            )
                        }
                    }
                }
            }
        }
    }

    // 添加睡眠对话框
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("记录睡眠时长") },
            text = {
                OutlinedTextField(
                    value = inputHours,
                    onValueChange = { inputHours = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("睡眠时长（小时）") },
                    singleLine = true,
                    placeholder = { Text("例如: 7.5") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val hours = inputHours.toFloatOrNull()
                    if (hours != null && hours > 0 && hours <= 24) {
                        viewModel.setSleep(hours)
                        inputHours = ""
                        showDialog = false
                    }
                }) {
                    Text("保存")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}