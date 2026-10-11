package com.example.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.data.BibleDataProvider

class DailyReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "daily_bible_study_reminder"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Daily Bible Study Reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminds you of your daily Setswana & English scripture reading and study goals."
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val randomVotd = BibleDataProvider.versesOfTheDay.random()
        val title = "Temana ya Gompieno / Verse of the Day"
        val message = "${randomVotd.first.bookNameSetswana} ${randomVotd.first.chapter}:${randomVotd.first.verse}\n${randomVotd.first.textSetswana}"

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_bible_launcher_1791749051522)
            .setContentTitle(title)
            .setContentText("${randomVotd.first.bookNameSetswana} ${randomVotd.first.chapter}:${randomVotd.first.verse}")
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
    }
}
