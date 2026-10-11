package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BibleDataProvider
import com.example.data.local.LocalReadingProgress
import com.example.data.local.LocalUserStreak
import com.example.model.BibleBook
import com.example.model.ReadingPlan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingPlanScreen(
    plans: List<ReadingPlan>,
    progressList: List<LocalReadingProgress>,
    userStreak: LocalUserStreak,
    onSelectDayReading: (BibleBook, Int) -> Unit,
    onToggleDayCompleted: (planId: String, dayNumber: Int) -> Unit,
    onOpenStreakDialog: () -> Unit,
    onBack: () -> Unit
) {
    var selectedPlanId by remember { mutableStateOf(plans.first().id) }
    val activePlan = plans.find { it.id == selectedPlanId } ?: plans.first()
    val activeProgress = progressList.find { it.planId == activePlan.id }

    val completedSet = activeProgress?.completedDayIndexesCsv
        ?.split(",")
        ?.filter { it.isNotBlank() }
        ?.mapNotNull { it.toIntOrNull() }
        ?.toSet() ?: emptySet()

    val completedCount = completedSet.size
    val totalDays = activePlan.totalDays
    val progressFraction = (completedCount.toFloat() / totalDays.toFloat()).coerceIn(0f, 1f)

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Thulaganyo ya Padi / Reading Plan", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Boela / Back")
                }
            },
            actions = {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFF6D00).copy(alpha = 0.15f),
                    modifier = Modifier
                        .clickable { onOpenStreakDialog() }
                        .padding(end = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = Color(0xFFFF6D00),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${userStreak.currentStreak} Days",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color(0xFFFF6D00)
                        )
                    }
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Plan Selector Cards
            item {
                Text(
                    text = "Dithulaganyo tse di Teng / Available Plans",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    plans.forEach { plan ->
                        val isSelected = plan.id == selectedPlanId
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            ),
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedPlanId = plan.id }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = plan.titleSetswana,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    maxLines = 2
                                )
                                Text(
                                    text = "${plan.totalDays} Matsatsi / Days",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Active Plan Progress Overview
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = activePlan.titleSetswana,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = activePlan.descriptionSetswana,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "$completedCount mo go $totalDays matsatsi a feditwe",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "${(progressFraction * 100).toInt()}% e feditswe",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Dithuto tsa Letsatsi le Letsatsi / Daily Readings",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Daily reading items list
            items(activePlan.days) { day ->
                val isCompleted = completedSet.contains(day.dayNumber)
                val book = BibleDataProvider.books.find { it.id == day.bookId } ?: BibleDataProvider.books.first()

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isCompleted) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Checkbox(
                                checked = isCompleted,
                                onCheckedChange = { onToggleDayCompleted(activePlan.id, day.dayNumber) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(
                                modifier = Modifier.clickable { onSelectDayReading(book, day.chapter) }
                            ) {
                                Text(
                                    text = "Letsatsi ${day.dayNumber}: ${day.titleSetswana}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${book.nameSetswana} ${day.chapter} (${book.nameEnglish} ${day.chapter})",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        IconButton(
                            onClick = { onSelectDayReading(book, day.chapter) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Bala / Read",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
