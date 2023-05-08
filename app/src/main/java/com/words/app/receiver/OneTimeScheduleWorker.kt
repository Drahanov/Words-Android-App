package com.words.app.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.words.MainActivity
import com.words.R
import java.util.*

/**
 * Created by enisademov on 24.11.21.
 */

class OneTimeScheduleWorker(
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    private val CHANNEL_ID = "SPADES_CHANNEL_ID"

    override fun doWork(): Result {
        val cal = Calendar.getInstance()
        val currentHourIn24Format: Int = cal.get(Calendar.HOUR_OF_DAY)

        val title = inputData.getString("NOTIFICATION_TITLE")
        val body = inputData.getString("NOTIFICATION_BODY")
        val id = inputData.getLong("NOTIFICATION_ID", 0).toInt()

        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, id, notificationIntent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(
                context,
                id,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        if (currentHourIn24Format in 10..20) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_check)
                .setLights(Notification.FLAG_SHOW_LIGHTS, 1000, 500)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOngoing(false)
                .setContentIntent(pendingIntent)
                .setStyle(NotificationCompat.BigTextStyle())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(CHANNEL_ID)
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Spades Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.enableLights(true)
                channel.lightColor = Color.WHITE
                channel.enableVibration(false)
                notificationManager.createNotificationChannel(channel)
            }

            with(NotificationManagerCompat.from(context)) {
                notify(id, builder.build())
            }

        }
        return Result.success()
    }
}
