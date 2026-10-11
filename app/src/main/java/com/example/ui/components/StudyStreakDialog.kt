package com.example.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.data.local.LocalUserStreak

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyStreakDialog(
    userStreak: LocalUserStreak,
    isReminderEnabled: Boolean,
    reminderHour: Int,
    reminderMinute: Int,
    onUpdateReminder: (enabled: Boolean, hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var reminderOn by remember { mutableStateOf(isReminderEnabled) }
    var selectedHour by remember { mutableIntStateOf(reminderHour) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            reminderOn = true
            onUpdateReminder(true, selectedHour, reminderMinute)
            Toast.makeText(context, "Tshedimosetso e buletswe / Reminder set!", Toast.LENGTH_SHORT).show()
        } else {
            reminderOn = false
            Toast.makeText(context, "Tetla ya dikitsiso e gannwe / Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    BasicAlertDialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = Color(0xFFFF6D00),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tswelelopele ya Padi / Study Streak",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Tswala")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Streak banner
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFF6D00).copy(alpha = 0.12f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "🔥 ${userStreak.currentStreak}",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF6D00)
                            )
                            Text(
                                text = "Matsatsi a Motseletsele\nCurrent Streak",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(40.dp)
                                .background(Color.Gray.copy(alpha = 0.3f))
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${userStreak.longestStreak}",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = "E e Kwa Godimo\nBest Streak",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Daily goal
                Text(
                    text = "Maikemisetso a Gompieno / Daily Verse Goal",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))

                val progress = (userStreak.versesReadToday.toFloat() / userStreak.dailyGoalVerses.toFloat()).coerceIn(0f, 1f)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${userStreak.versesReadToday} mo go ${userStreak.dailyGoalVerses} ditsopo",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (progress >= 1f) "O fitlheletse! Goal reached 🎉" else "${(progress * 100).toInt()}%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))

                // Push notifications study reminder
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Kitsiso ya Thuto / Daily Reminder",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "0${selectedHour}:00 mo mosong / morning",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Switch(
                        checked = reminderOn,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                                    ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    reminderOn = true
                                    onUpdateReminder(true, selectedHour, reminderMinute)
                                    Toast.makeText(context, "Kitsiso e rulagantswe / Reminder set", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                reminderOn = false
                                onUpdateReminder(false, selectedHour, reminderMinute)
                                Toast.makeText(context, "Kitsiso e timilwe / Reminder cancelled", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go siame / Got it")
                }
            }
        }
    }
}
