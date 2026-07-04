package com.example.healthsimulator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthsimulator.ui.theme.*
import com.example.healthsimulator.viewmodel.HealthViewModel

@Composable
fun DashboardScreen(viewModel: HealthViewModel) {
    val todayData by viewModel.todayData.collectAsState()
    val weeklySteps by viewModel.weeklySteps.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "今日健康概览",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 四项指标卡片
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HealthMetricCard(
                title = "步数",
                value = "${todayData.steps}",
                unit = "步",
                goal = "目标: 10000",
                icon = Icons.Default.DirectionsWalk,
                color = StepColor,
                modifier = Modifier.weight(1f)
            )
            HealthMetricCard(
                title = "饮水",
                value = "${todayData.waterCups}",
                unit = "杯",
                goal = "目标: 8杯",
                icon = Icons.Default.WaterDrop,
                color = WaterColor,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HealthMetricCard(
                title = "睡眠",
                value = String.format("%.1f", todayData.sleepHours),
                unit = "小时",
                goal = "目标: 8小时",
                icon = Icons.Default.Bedtime,
                color = SleepColor,
                modifier = Modifier.weight(1f)
            )
            HealthMetricCard(
                title = "卡路里",
                value = "${todayData.calories}",
                unit = "大卡",
                goal = "目标: 2000",
                icon = Icons.Default.Restaurant,
                color = CalorieColor,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 本周步数趋势
        Text(
            text = "本周步数趋势",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (weeklySteps.isEmpty()) {
                    Text(
                        text = "暂无数据，开始记录你的健康数据吧！",
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    weeklySteps.forEach { day ->
                        val dayLabel = day.date.substringAfterLast("-") + "日"
                        val progress = (day.steps.toFloat() / 10000f).coerceAtMost(1f)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = dayLabel,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.width(40.dp)
                            )
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(12.dp),
                                color = StepColor,
                                trackColor = Green100,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${day.steps}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.width(60.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 健康小贴士
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Green50)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Green700,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "健康小贴士",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Green900
                    )
                    Text(
                        text = "每天喝8杯水、走10000步、睡8小时，\n保持均衡饮食，健康生活每一天！",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Green700
                    )
                }
            }
        }
    }
}

@Composable
fun HealthMetricCard(
    title: String,
    value: String,
    unit: String,
    goal: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = unit,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = goal,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}